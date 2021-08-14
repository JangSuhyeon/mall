var order = {
    init : function () {
        var _this = this;
        $('#choice-btn').on('click',function () {
            _this.goToChoiceOrder();
        });
        $('#all-choice-btn').on('click',function () {
            _this.goToALlChoiceOrder();
        });
        $('#delete-cart-btn').on('click',function () {
            _this.deleteCart();
        });
    },

    goToChoiceOrder : function () {
        var id = $('.userId').val();
        var checkArr = [];

        var count = $('.checkbox:checked').get().length;
        if(count <= 0) {
            alert("작품을 1개 이상 선택해주세요.");
            return false;
        }

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
    },

    deleteCart : function () {
        var id = $('.hidden-art-id').val();

        $.ajax({
            type: 'DELETE',
            url: '/order/delete/' + id,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8'
        }).done(function () {
            window.location.href = '/order/cart';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    }
}

order.init();