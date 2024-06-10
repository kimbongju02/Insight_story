var submitButton = document.getElementById("submitButton");

// 확인 버튼 클릭 시 입력값 확인
submitButton.onclick = function() {
    var apiKey = document.getElementById("apiKeyInput").value;
    if (apiKey.length === 56) {
        window.parent.postMessage('closeModal', '*');
        window.parent.postMessage('success', '*');
    } else {
        window.parent.postMessage('error', '*');
    }
};