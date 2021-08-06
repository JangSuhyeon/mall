var main = {
    init : function () {
        var _this = this;
        $('#btn-save').on('click',function () {
            _this.save();
        });
        $('#btn-update').on('click',function () {
            _this.update();
        });
        $('#btn-delete').on('click',function () {
            _this.delete();
        });
    },

    save : function () {
        var data = {
            title: $('#title').val(),
            artist: $('#artist').val(),
            description: $('#description').val(),
            img: $('#img').val(),
            price: $('#price').val(),
            categoryId: $('#category').val()
        };
        var category = $('#category').val();
        var form=$('#form')[0];
        var formData = new FormData(form);
        formData.append('file',$('#file'));
        formData.append('key', new Blob([JSON.stringify(data)], {type: "application/json"}));
        $.ajax({
            type: 'POST',
            url: '/api/v1/art',
            processData: false,
            contentType: false,
            data: formData
        }).done(function () {
            alert('작품이 등록되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    update : function () {
        var data = {
            title: $('#title').val(),
            artist: $('#artist').val(),
            description: $('#description').val(),
            price: $('#price').val(),
            artImageId: $('#artImageId').val()
        };

        var form=$('#form')[0];
        var formData = new FormData(form);
        formData.append('file',$('#file'));
        formData.append('key', new Blob([JSON.stringify(data)], {type: "application/json"}));
        var id = $('#id').val();

        $.ajax({
            type: 'PUT',
            url: '/api/v1/art/' + id,
            processData: false,
            contentType: false,
            data: formData
        }).done(function () {
            alert('글이 수정되었습니다.');
            window.location.href = '/art/detail/' + id;
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    delete : function () {
        var id = $('#id').val();

        $.ajax({
            type: 'DELETE',
            url: '/api/v1/art/' + id,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8'
        }).done(function () {
            alert('글이 삭제되었습니다.');
            window.location.href = '/art/list';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    }

}

main.init();