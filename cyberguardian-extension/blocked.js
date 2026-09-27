const params = new URLSearchParams(window.location.search);

const reason = params.get("reason");
const blockedUrl = params.get("url");
const childId = params.get("childId");
console.log("Blocked URL:", blockedUrl);
console.log("Block reason:", reason);
console.log("Child ID:", childId);
if (reason === "PARENT_POLICY") {

    const requestButton = document.createElement("button");

    requestButton.textContent = "Request Access";

    document.body.appendChild(requestButton);

    requestButton.addEventListener("click", () => {

        console.log("Request Access clicked");


        const requestData = {
            child: {
                childId: childId
            },
            url: blockedUrl,
            status: "PENDING"
        };

        fetch("http://localhost:8080/api/access-requests", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(requestData)
        })
            .then(async response => {

                console.log("ACCESS REQUEST STATUS:", response.status);

                const text = await response.text();

                console.log("ACCESS REQUEST RESPONSE:", text);

                if (!response.ok) {
                    throw new Error(
                        "Access request failed with status " + response.status
                    );
                }

                return text;
            })
            .then(data => {

                console.log("Access request submitted:", data);

                requestButton.textContent = "Request Sent";
                requestButton.disabled = true;

            })
            .catch(error => {

                console.error(
                    "Access request failed:",
                    error
                );

            });
    });
}