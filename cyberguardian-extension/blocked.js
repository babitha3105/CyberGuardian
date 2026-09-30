const params = new URLSearchParams(window.location.search);

const reason = params.get("reason");
const blockedUrl = params.get("url");
const childId = params.get("childId");

console.log("Blocked URL:", blockedUrl);
console.log("Block reason:", reason);
console.log("Child ID:", childId);


if (reason === "PARENT_POLICY") {

    fetch(
        `http://localhost:8080/api/access-requests/child/${childId}`
    )
        .then(async response => {

            console.log(
                "ACCESS REQUEST CHECK STATUS:",
                response.status
            );

            const text = await response.text();

            console.log(
                "ACCESS REQUEST CHECK RESPONSE:",
                text
            );

            if (!response.ok) {
                throw new Error(
                    "Access request check failed with status "
                    + response.status
                );
            }

            return JSON.parse(text);
        })
        .then(requests => {

            console.log(
                "Access requests received:",
                requests
            );

            const matchingRequests = requests
                .filter(request => request.url === blockedUrl)
                .sort(
                    (a, b) =>
                        new Date(b.requestedAt) -
                        new Date(a.requestedAt)
                );

            const matchingRequest = matchingRequests[0];

            // APPROVED
            if (
                matchingRequest &&
                matchingRequest.status === "APPROVED"
            ) {

                console.log(
                    "Access already approved. Opening website..."
                );

                window.location.href = blockedUrl;

                return;
            }

// PENDING
            if (
                matchingRequest &&
                matchingRequest.status === "PENDING"
            ) {

                console.log(
                    "Access request is already pending."
                );

                const pendingMessage =
                    document.createElement("p");

                pendingMessage.textContent =
                    "Access request is pending. Waiting for parent approval...";

                document.body.appendChild(
                    pendingMessage
                );

                // Check again after 5 seconds
                setTimeout(() => {
                    window.location.reload();
                }, 5000);

                return;
            }


            // DENIED or no previous request
            console.log(
                "No active access request found."
            );

            const requestButton =
                document.createElement("button");

            requestButton.textContent =
                "Request Access";

            document.body.appendChild(
                requestButton);


            requestButton.addEventListener("click", () => {

                console.log(
                    "Request Access clicked"
                );

                const requestData = {

                    child: {
                        childId: childId
                    },

                    url: blockedUrl,

                    status: "PENDING"
                };


                fetch(
                    "http://localhost:8080/api/access-requests",
                    {
                        method: "POST",

                        headers: {
                            "Content-Type":
                                "application/json"
                        },

                        body:
                            JSON.stringify(requestData)
                    }
                )
                    .then(async response => {

                        console.log(
                            "ACCESS REQUEST STATUS:",
                            response.status
                        );

                        const text =
                            await response.text();

                        console.log(
                            "ACCESS REQUEST RESPONSE:",
                            text
                        );

                        if (!response.ok) {

                            throw new Error(
                                "Access request failed with status "
                                + response.status
                            );
                        }

                        return text;
                    })
                    .then(data => {

                        console.log(
                            "Access request submitted:",
                            data
                        );

                        requestButton.textContent =
                            "Request Sent";

                        requestButton.disabled =
                            true;

                        console.log(
                            "Waiting for parent approval..."
                        );

                        setTimeout(() => {

                            console.log(
                                "Checking access request status again..."
                            );

                            window.location.reload();

                        }, 5000);

                    })
                    .catch(error => {

                        console.error(
                            "Access request failed:",
                            error
                        );

                    });

            });

        })
        .catch(error => {

            console.error(
                "Access request check failed:",
                error
            );

        });
}