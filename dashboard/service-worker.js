importScripts(
    "https://www.gstatic.com/firebasejs/12.3.0/firebase-app-compat.js"
);

importScripts(
    "https://www.gstatic.com/firebasejs/12.3.0/firebase-messaging-compat.js"
);


const firebaseConfig = {

    apiKey: "AIzaSyDvFshC6S4oBIUPx8HwLyaXnbdpHOV8rg",

    authDomain:
        "cyberguardian-3a01c.firebaseapp.com",

    projectId:
        "cyberguardian-3a01c",

    storageBucket:
        "cyberguardian-3a01c.firebasestorage.app",

    messagingSenderId:
        "87226357807",

    appId:
        "1:87226357807:web:6f5ed297bbcc46d0660eda",

    measurementId:
        "G-T09RCWNB6W"
};


firebase.initializeApp(firebaseConfig);

const messaging = firebase.messaging();
const CACHE_NAME = "cyberguardian-v1";

const FILES_TO_CACHE = [
    "./",
    "./index.html",
    "./policies.html",
    "./manifest.json"
];

self.addEventListener("install", event => {

    event.waitUntil(
        caches.open(CACHE_NAME)
            .then(cache => {
                return cache.addAll(FILES_TO_CACHE);
            })
    );

    self.skipWaiting();
});

self.addEventListener("activate", event => {

    event.waitUntil(
        self.clients.claim()
    );
});

self.addEventListener("fetch", event => {

    event.respondWith(
        caches.match(event.request)
            .then(response => {
                return response || fetch(event.request);
            })
    );
});


messaging.onBackgroundMessage((payload) => {

    console.log(
        "Background notification received:",
        payload
    );

    const notificationTitle =
        payload.notification?.title ||
        "CyberGuardian Alert";

    const notificationOptions = {

        body:
            payload.notification?.body ||
            "A new security alert was received.",

        icon: "./icons/icon-192.png",

        data: payload.data || {}
    };

    self.registration.showNotification(
        notificationTitle,
        notificationOptions
    );
});