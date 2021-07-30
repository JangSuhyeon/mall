var order = {
    init : function () {
        var _this = this;
        $('#choice-btn').on('click',function () {
            _this.goToChoiceOrder();
        });
    },

    goToChoiceOrder : function () {
        // name이 같은 체크박스의 값들을 배열에 담는다.
        var checkboxValues = [];
        $("input[name='checkbox']:checked").each(function(i) {
            checkboxValues.push($(this).prev('.hidden-art-id').val());
        });

        // 사용자 ID(문자열)와 체크박스 값들(배열)을 name/value 형태로 담는다.
        var allData = { "artIdList": checkboxValues };

        $.ajax({
            url: "/order/order",
            type: 'GET',
            data: allData
        }).done(function () {
            alert('주문서 이동');
            window.location.href = '/order/order';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    }
}

order.init();