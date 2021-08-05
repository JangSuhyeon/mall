$(document).ready(function(){
    count(); //처음 전체 선택된 상품의 갯수
    cart.allAddBuyList(); //처음 전체 선택된 상품의 총금액

    function count() { //선택한 상품 갯수
        var init_count = $('input:checkbox[name=checkbox]:checked').length;
        $('.total').text(init_count);
    }

    // checkbox 변경 시
    $(".checkbox").change(function() {
        count();
        if($(this).is(":checked")){
            var tr = $(this).closest('tr'); //변경된 checkBox의 tr
            var price = Number(tr.children('td.price').children('span').text().replace(/[^\d]+/g, "")); //tr안에 price 가격 가져오기
            var id = tr.children('td.name').children('.hidden-art-id').val();

            var obj = {
                id : id,
                price : price
            }

            cart.addBuyList(obj);
        }else {
            var cd = $(this).closest('tr').children('td.name').children('.hidden-art-id').val();
            cart.deleteBuyList(cd);
        }
    });
});

var cart ={
    buyList : [], //checked Art List
    init : function () {
        //buyList 초기화
        this.buyList = [];
    },
    allAddBuyList : function () {
        this.init();

        //전체 checked Art 가져오기
        $(top.document).find('.checkbox').each(function(){
            var tr = $(this).closest('tr'); //변경된 checkBox의 tr
            var price = Number(tr.children('td.price').children('span').text().replace(/[^\d]+/g, "")); //tr안에 price 가격 가져오기
            var id = tr.children('td.name').children('.hidden-art-id').val();

            var obj = {
                id : id,
                price : price
            }

            cart.addBuyList(obj);
        });
    },
    addBuyList : function(obj) {
        //buyList에 Art 추가
        this.buyList.push(obj);
        this.getTotalPay();
    },
    deleteBuyList : function(cd) {
        this.buyList.splice(this.getFindIndex(cd), 1);
        this.getTotalPay();
    },
    getTotalPay : function() {
        //총 결제금액 계산
        var totalPrice = 0;

        this.buyList.forEach(function(obj) {
            totalPrice += Number(obj.price);
        });
        $('#totalPrice').text(String(totalPrice).replace(/\B(?=(\d{3})+(?!\d))/g, ","));
    },
    getFindIndex : function(cd) {
        //배열 중복 검색
        var fIdx = -1;
        this.buyList.forEach(function(item,index,array) {
            if(item.id == cd) {
                fIdx = index;
            }
        });
        return fIdx;
    }
}