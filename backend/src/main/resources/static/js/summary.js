const summaryItems = {};  // Summary 항목 저장 객체

// '추가' 버튼 이벤트 설정
document.querySelectorAll('.btn-outline-dark').forEach((button) => {
    button.addEventListener('click', function (event) {
        event.preventDefault();  // 페이지 리로드 방지

        // 상품 정보 가져오기
        const productCard = this.closest('li');
        const productName = productCard.querySelector('.row:nth-child(2)').textContent.trim();
        const productPrice = parseInt(productCard.querySelector('.price').textContent.replace('원', '').trim());

        // Summary에 추가 또는 수량 증가
        if (summaryItems[productName]) {
            summaryItems[productName].count += 1;
        } else {
            summaryItems[productName] = { name: productName, price: productPrice, count: 1 };
        }

        updateSummary();  // Summary 업데이트
    });
});

// Summary 업데이트 함수
function updateSummary() {
    const summaryContainer = document.getElementById('summary-container');
    summaryContainer.innerHTML = '';  // 기존 항목 초기화
    let totalPrice = 0;

    // Summary 항목 추가
    for (const [key, item] of Object.entries(summaryItems)) {
        const summaryRow = document.createElement('div');
        summaryRow.classList.add('row');
        summaryRow.innerHTML = `<h6 class="p-0">${item.name} <span class="badge bg-dark">${item.count}개</span></h6>`;
        summaryContainer.appendChild(summaryRow);

        totalPrice += item.price * item.count;  // 총 금액 계산
    }

    // 총 금액 업데이트
    document.getElementById('total-price').textContent = `${totalPrice}원`;
}
