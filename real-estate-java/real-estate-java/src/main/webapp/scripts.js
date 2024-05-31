document.addEventListener('DOMContentLoaded', function() {
    // Form validation and other JS functionalities

    // Register Form Validation
    document.getElementById('registerForm').addEventListener('submit', function(event) {
        const password = document.getElementById('password').value;
        if (password.length < 6) {
            alert('Password must be at least 6 characters long.');
            event.preventDefault();
        }
    });

    // Login Form Validation
    document.getElementById('loginForm').addEventListener('submit', function(event) {
        const email = document.getElementById('email').value;
        const password = document.getElementById('password').value;
        if (!email || !password) {
            alert('Please fill out both email and password fields.');
            event.preventDefault();
        }
    });

    // Post Property Form Validation
    document.getElementById('postPropertyForm').addEventListener('submit', function(event) {
        // Add any necessary validation for the property form
    });

    // Example of handling the like button via AJAX
    document.querySelectorAll('.like-button').forEach(function(button) {
        button.addEventListener('click', function() {
            const propertyId = this.dataset.propertyId;
            fetch('/like', {
                method: 'POST',
                body: JSON.stringify({ propertyId: propertyId }),
                headers: {
                    'Content-Type': 'application/json'
                }
            }).then(response => response.text())
              .then(data => {
                  if (data === 'success') {
                      const countElement = document.querySelector(`#like-count-${propertyId}`);
                      countElement.textContent = parseInt(countElement.textContent) + 1;
                  }
              });
        });
    });
});
