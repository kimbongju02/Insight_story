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
        
        if(data==="s")
            goToList();
        else{
            error_element=document.getElementById("errorMessage");
            error_element.textContent="유효하지 않은 키 입력";
        }
    } catch (error) {
        console.error('오류 발생:', error); // 네트워크 오류 등 예외 처리
    }
}

function goToList(){
    const url = "/list";
    window.location.href = url; 
}