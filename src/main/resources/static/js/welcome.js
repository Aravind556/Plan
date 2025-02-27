// document.addEventListener("DOMContentLoaded", function () {
//     // Fetch username from localStorage or backend session
//     const username = localStorage.getItem("username") || "Guest";
//     document.getElementById("username").textContent = username;
//
//     // Logout functionality
//     document.getElementById("logoutBtn").addEventListener("click", function () {
//         localStorage.removeItem("username"); // Clear stored user
//         window.location.href = "login.html"; // Redirect to login page
//     });
// });

// Script to handle welcome page functionality
document.addEventListener('DOMContentLoaded', function() {
    // Get username from localStorage or URL params if available
    const params = new URLSearchParams(window.location.search);
    const usernameParam = params.get('username');
    const storedUsername = localStorage.getItem('username');

    const username = usernameParam || storedUsername || 'User';

    // Update the username in the welcome message
    document.getElementById('username').textContent = username;

    // Store username in localStorage for future visits
    if (usernameParam) {
        localStorage.setItem('username', usernameParam);
    }

    // Add event listener for logout button
    const logoutBtn = document.getElementById('logoutBtn');
    logoutBtn.addEventListener('click', function() {
        // Additional client-side logout logic can be added here if needed
        localStorage.removeItem("username");
        window.location.href = "login.html";}