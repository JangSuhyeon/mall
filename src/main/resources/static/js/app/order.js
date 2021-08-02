var order = {
    init : function () {
        var _this = this;
        $('#choice-btn').on('click',function () {
            _this.goToChoiceOrder();
        });
        $('#all-choice-btn').on('click',function () {
            _this.goToALlChoiceOrder();
        });
    },

    goToChoiceOrder : function () {
        var id = $('.userId').val();
        var checkArr = [];
        $('.checkbox:checked').each(function () {
            checkArr.push($(this).val());
        });

        $.ajax({
            url: "/order/order",
            type: 'GET',
            data: {
                art_id : checkArr
            }
        }).done(function () {
            window.location.href = '/order/order/'+id;
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },

    goToALlChoiceOrder : function () {
        var id = $('.userId').val();
        var checkArr = [];
        $('.checkbox').each(function () {
            checkArr.push($(this).val());
        });

        $.ajax({
            url: "/order/order",
            type: 'GET',
            data: {
                art_id : checkArr
            }
        }).done(function () {
            window.location.href = '/order/order/'+id;
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    }
}

order.init();