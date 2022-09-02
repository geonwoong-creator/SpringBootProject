<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<script src="https://code.jquery.com/jquery-latest.min.js"></script>
<script>
    var notice_seq = '<%=CmmUtil.nvl(rDTO.getNotice_seq())%>';

    function commentList(){
        $.ajax({
            url : '/comment/list',
            type : 'get',
            data : {'notice_seq':notice_seq},
            success : function (data){
                var a = '';
                $.each(data, function (key, value){
                    a += '<div class="container" style="border-bottom:1px solid darkgray; margin-bottom: 15px;">';
                    a += '<div class="card-header' + value.cno + ' ">' + '댓글번호 : ' + value.cno + '/작성자 :' + value.user_id;
                    a += '<br>'
                    a += '<button class="btn btn-warning" onclick="commentUpdate(' + value.cno + ',\'' + value.content + '\');"> 수정 </button>';
                    a += '<button class="btn btn-danger" onclick="commentDelete(' + value.cno + ');"> 삭제 </button> </div>';
                    a += '<div class="card-body' + value.cno + '"> <p> 내용 : ' + value.content + '</p>';
                    a += '</div></div>';

                });
                $("#commentList").html(a);
            }
        });
    }

    //댓글 등록
    function commentInsert(){
        let noticeSeq = $('#notice_seq').val();
        let content = $('#content').val();
        $.ajax({
            url : '/comment/insert',
            type : 'post',
            data : {
                "notice_seq" : noticeSeq,
                "content" : content
            },
            success : function (data){
                if(data == 1) {
                    commentList();
                    $('[name=cotent]').val('');
                }
            }
        });
    }

    //댓글 수정 - 댓글 내용 출력을 input 폼으로 변경
    function commentUpdate(cno, content){
        var a ='';

        a += '<div class="input-group">';
        a += '<input type="text" class="form-control" name="content_' + cno + '" value="' + content + '"/>';
        a += '<span class="input-group-btn"><button class="btn btn-outline-success" type="button" onclick="commentUpdateProc('+cno+');">수정</button> </span>';
        a += '</div>';

        $('.commentContent' + cno).html(a);
    }

    //댓글 수정
    function commentUpdateProc(cno){
        var updateContent = $('[name=content_' + cno +']').val();

        $.ajax({
            url : '/comment/update',
            type : 'post',
            data : {'content' : updateContent, 'cno' : cno},
            success : function (data){
                if (data == 1) commentList(notice_seq); //댓글 수정후 목록 출력
            }
        });
    }

    //댓글 삭제
    function commentDelete(cno){
        $.ajax({
            url : '/comment/delete/' + cno,
            type : 'post',
            success : function (data) {
                if (data == 1) commentList(notice_seq); //댓글 삭제후 목록 출력
            }
        });
    }

    $(document).ready(function (){
        commentList(); //페이지 로딩시 댓글 목로 출력
    });
</script>
