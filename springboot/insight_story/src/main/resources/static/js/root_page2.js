document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('apiKeyForm');
    const input = document.getElementById('apiKeyInput');
    const errorMessage = document.getElementById('errorMessage');
    
    hideError();
    
    form.addEventListener('submit', function(event) {
        if (!input.checkValidity()) {
            event.preventDefault(); // 폼 제출 방지
            showError('유효하지 않은 키 입력');
        } else {
            hideError();
        }
    });

    input.addEventListener('input', function() {
        if (input.validity.valid) {
            hideError();
        }
    });

    input.addEventListener('invalid', function(event) {
        event.preventDefault(); // 기본 브라우저 메시지 비활성화
        showError('유효하지 않은 키 입력');
    });

    function showError(message) {
        errorMessage.textContent = message;
        errorMessage.style.display = 'block';
        input.classList.add('invalid');
    }

    function hideError() {
        errorMessage.textContent = '';
        errorMessage.style.display = 'none';
        input.classList.remove('invalid');
    }
});
