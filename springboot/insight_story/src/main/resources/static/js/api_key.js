var submitButton = document.getElementById("submitButton");

async function api_key_register(api_key) {
    try {
        const response = await fetch('/saveApiKey', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                key: api_key
            })
        });
        const data = await response.text(); // 응답을 텍스트 형식으로 받음
        return data;
    } catch (error) {
        console.error('에러 발생:', error);
        return 'error';
    }
}

submitButton.onclick = async function() {
    var apiKey = document.getElementById("apiKeyInput").value;
    var api_key_set_result = await api_key_register(apiKey);
    console.log(api_key_set_result);
    if (api_key_set_result === 's') {
        window.parent.postMessage('closeModal', '*');
        window.parent.postMessage('success', '*');
    } else {
        window.parent.postMessage('error', '*');
    }
};
