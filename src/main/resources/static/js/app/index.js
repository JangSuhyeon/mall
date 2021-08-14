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
        //저장할 값 가져오기
        var data = {
            title: $('#title').val(),
            artist: $('#artist').val(),
            description: $('#description').val(),
            price: $('#price').val(),
            categoryId: $('#category').val()
        };
        var file = $('#file').val();

        //값 유효성 체크
        if (this.check(data,file) == false) {
            return false
        };

        var form=$('#form')[0];
        var formData = new FormData(form);
        formData.append('file',file);
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
            categoryId: $('#category').val(),
            artImageId: $('#artImageId').val()
        };
        var file = $('#file').val();

        //값 유효성 체크
        if (this.checkNoFile(data) == false) {
            return false
        };

        var form=$('#form')[0];
        var formData = new FormData(form);
        formData.append('file',file);
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
    },
    check : function (data,file) {
        if (!jQuery.isNumeric(data.categoryId)){
            alert('카테고리를 선택해주세요.');
            $('#bigCategory').focus();
            return false;
        }else if(data.title.length==0){
            alert('작품 제목을 입력해주세요.');
            $('#title').focus();
            return false;
        }else if (data.artist.length==0){
            alert('작가를 입력해주세요.');
            $('#artist').focus();
            return false;
        }else if (data.description.length==0){
            alert('작품 간단 설명을 입력해주세요.');
            $('#description').focus();
            return false;
        }else if (data.price.length==0){
            alert('작품 판매금액을 등록해주세요.');
            $('#price').focus();
            return false;
        }else if (!file){
            alert('작품 썸네일 이미지를 등록해주세요.');
            return false;
        }
    },
    checkNoFile : function (data) {
        if (!jQuery.isNumeric(data.categoryId)) {
            alert('카테고리를 선택해주세요.');
            $('#bigCategory').focus();
            return false;
        } else if (data.title.length == 0) {
            alert('작품 제목을 입력해주세요.');
            $('#title').focus();
            return false;
        } else if (data.artist.length == 0) {
            alert('작가를 입력해주세요.');
            $('#artist').focus();
            return false;
        } else if (data.description.length == 0) {
            alert('작품 간단 설명을 입력해주세요.');
            $('#description').focus();
            return false;
        } else if (data.price.length == 0) {
            alert('작품 판매금액을 등록해주세요.');
            $('#price').focus();
            return false;
        }
    }
}

main.init();