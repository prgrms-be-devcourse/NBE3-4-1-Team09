 function openKakaoPostcode() {
      new daum.Postcode({
          oncomplete: function(data) {
              // 도로명 주소와 우편번호 가져오기
              const roadAddr = data.roadAddress; // 도로명 주소
              const postcode = data.zonecode;   // 우편번호

              // 값 입력
              document.getElementById('address').value = roadAddr;
              document.getElementById('postcode').value = postcode;
          }
      }).open();
  }

  // '주소 검색' 버튼 클릭 이벤트
  document.getElementById('search-address').addEventListener('click', openKakaoPostcode);