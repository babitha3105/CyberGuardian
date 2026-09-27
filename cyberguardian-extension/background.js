console.log("CyberGuardian extension started");
const connectionCode = "B668C3";
let childId = null;
chrome.alarms.create("heartbeat", {
   periodInMinutes: 1
});

chrome.alarms.onAlarm.addListener((alarm) => {

    if (alarm.name === "heartbeat" && childId !== null) {

        fetch(
            `http://localhost:8080/api/heartbeat?childId=${childId}`,
            {
                method: "POST"
            }
        )
            .then(async response => {
                console.log("HEARTBEAT STATUS:", response.status);

                const text = await response.text();
                console.log("HEARTBEAT RESPONSE:", text);

                if (!response.ok) {
                    throw new Error("Heartbeat failed with status " + response.status);
                }

                return JSON.parse(text);
            })
            .then(data => {
                console.log(
                    "Heartbeat sent. Last seen:",
                    data.lastSeen
                );
            })
            .catch(error => {
                console.error(
                    "Heartbeat failed:",
                    error
                );
            });
    }
});

fetch(
    `http://localhost:8080/api/child-connection?connectionCode=${connectionCode}`
)
    .then(async response => {
        console.log("STATUS:", response.status);

        const text = await response.text();
        console.log("RESPONSE:", text);

        if (!response.ok) {
            throw new Error("Request failed with status " + response.status);
        }

        return JSON.parse(text);
    })
        .then(child => {

        childId = child.childId;

        console.log("CyberGuardian connected to child:", childId);
    })
    .catch(error => {
        console.error("Child connection failed:", error);
    });
chrome.tabs.onUpdated.addListener((tabId, changeInfo, tab) => {

    if (
        changeInfo.status === "complete" &&
        tab.url &&
        (tab.url.startsWith("http://") || tab.url.startsWith("https://"))
    ) {

        console.log("Website visited:", tab.url);

        if (childId === null) {
            console.log("Child not connected yet. Skipping website check.");
            return;
        }
        // -----------------------------
        // SEARCH DETECTION
        // -----------------------------

        const url = new URL(tab.url);

        if (url.hostname.includes("google.com") && url.pathname === "/search") {

            const searchQuery = url.searchParams.get("q");

            if (searchQuery) {
                console.log("Search detected:", searchQuery);

                const searchData = {
                    child: {
                        childId: childId
                    },
                    searchQuery: searchQuery
                };

                fetch("http://localhost:8080/api/search-history", {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify(searchData)
                })
                    .then(response => response.json())
                    .then(data => {
                        console.log("Search history saved:", data);
                    })
                    .catch(error => {
                        console.error("Search history save failed:", error);
                    });
            }
        }

        // -----------------------------
        // WEBSITE CHECK
        // -----------------------------

        fetch(
            `http://localhost:8080/api/website-check?childId=${childId}&url=${encodeURIComponent(tab.url)}`
        )
            .then(async response => {
                console.log("WEBSITE CHECK STATUS:", response.status);

                const text = await response.text();
                console.log("WEBSITE CHECK RESPONSE:", text);

                if (!response.ok) {
                    throw new Error(
                        "Website check failed with status " + response.status
                    );
                }

                return JSON.parse(text);
            })
            .then(result => {

                console.log("Backend response:", result);
                console.log("Decision:", result.decision);
                console.log("Reason:", result.reason);

                // Save browsing history
                const historyData = {
                    child: {
                        childId: childId
                    },
                    url: tab.url,
                    category: "GENERAL",
                    riskLevel: result.decision === "BLOCK" ? "HIGH" : "LOW",
                    actionTaken: result.decision                };

                fetch("http://localhost:8080/api/browsing-history", {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify(historyData)
                })
                    .then(async response => {
                        console.log("HISTORY STATUS:", response.status);

                        const text = await response.text();
                        console.log("HISTORY RESPONSE:", text);

                        if (!response.ok) {
                            throw new Error(
                                "History save failed with status " + response.status
                            );
                        }

                        return JSON.parse(text);
                    })                    .then(data => {
                        console.log("Browsing history saved:", data);
                    })
                    .catch(error => {
                        console.error("History save failed:", error);
                    });

                // Block website if necessary
                if (result.decision === "BLOCK") {

                    const blockedUrl = encodeURIComponent(tab.url);
                    const reason = encodeURIComponent(result.reason);
                    const child = encodeURIComponent(childId);

                    chrome.tabs.update(tabId, {
                        url: chrome.runtime.getURL(
                            `blocked.html?url=${blockedUrl}&reason=${reason}&childId=${child}`
                        )
                    });
                }

            })
            .catch(error => {
                console.error("Backend connection failed:", error);
            });
    }
});