// 모달 열기
window.onload = function() {
    var modal = document.getElementById("keyModal");
    modal.style.display = "block";

    // 모달 창이 로드되었을 때 메시지 수신
    window.addEventListener('message', function(event) {
        if (event.data === 'closeModal') {
            modal.style.display = "none";
        } else if (event.data === 'error') {
            swal({
                title: "오류!",
                text: `OpenAi API Key는 정확히 56자여야 합니다!
                키를 다시 확인해 주세요.`,
                icon: "warning",
                button: "확인",
                dangerMode: true
            });
        } else if (event.data === 'success') {
            swal({
              title: "확인 완료!",
              text: "OpenAi API Key가 확인되었습니다!",
              icon: "success",
              button: "확인",
            });
        }
    });
};

function goToNextPage(keyword) {
    window.location.href = "/index/"+keyword;
}