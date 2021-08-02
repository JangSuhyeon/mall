var order = {
    init : function () {
        var _this = this;
        $('#choice-btn').on('click',function () {
            _this.goToChoiceOrder();
        });
    },

    goToChoiceOrder : function () {
        var checkArr = [];
        $('.checkbox:checked').each(function () {
            checkArr.push($(this).val());
        });
        alert(checkArr);

        $.ajax({
            url: "/order/order",
            type: 'GET',
            data: {
                art_id : checkArr
            }
        }).done(function () {
            alert('주문서 이동');
            window.location.href = '/order/order';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    }
}

order.init();