function shortenUrl() {
    const url = document.getElementById("longUrl").value;

    fetch("http://localhost:8081/shorten", {
        method: "POST",
        body: url
    })
    .then(res => res.json())
    .then(data => {
        document.getElementById("result").innerHTML =
            `Short URL: <a href="${data.shortUrl}" target="_blank">${data.shortUrl}</a>`;
    })
    .catch(err => {
        document.getElementById("result").innerText = "Failed to shorten URL.";
        console.error("Error:", err);
    });
}
