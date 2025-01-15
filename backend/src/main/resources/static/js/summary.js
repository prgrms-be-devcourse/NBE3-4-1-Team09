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

//  order 및 orderDetail에 날릴 파라미터들
document.querySelector('.btn-dark').addEventListener('click', function (event) {
    event.preventDefault();  // 페이지 새로고침 방지

    // 사용자 입력값 가져오기
    const email = document.getElementById('email').value.trim();
    const address = document.getElementById('address').value.trim();
    const postcode = document.getElementById('postcode').value.trim();

    // 상품 데이터 정리
    const products = Object.values(summaryItems).map(item => ({
        id: item.id,  // 상품 ID
        name: item.name,
        quantity: item.count,  // 수량
        price: item.price  // 단가
    }));

    // 총액 계산
    const totalAmount = products.reduce((sum, item) => sum + item.price * item.quantity, 0);

    // `form`에 데이터 설정
    document.getElementById('products').value = JSON.stringify(products);  // 숨겨진 input에 JSON 문자열 추가
    document.getElementById('totalAmount').value = totalAmount;

    // Form 제출
    document.getElementById('order-form').submit();
});
