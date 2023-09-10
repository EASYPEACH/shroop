// src/service-worker.js or src/service-worker.ts
self.addEventListener("fetch", (event) => {
  event.respondWith(fetch(event.request));
});
