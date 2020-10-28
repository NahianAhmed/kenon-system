<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<div class="row">

    <div class="col-10 offset-1">
        <form method="post" action="/admin/user-upload" enctype="multipart/form-data" class="pl-3 m-4" style="font-size: 13px">

            <p style="color: red">${error}</p>
            <div class="pt-4">
                <h4>ユーザの取り込み</h4>
            </div>
            <a href="/admin/user-download" class="btn btn-primary form-control" style="width: 230px">登録用最新ファイル</a>

            <div class="form-group">
                <div class="pt-4">
                    <h4>取り込み用ファイル</h4>
                </div>

                <input type="file" name="file" class="form-control" id="title" style="width: 230px" placeholder="ファイルを選択"  required>

            </div>
            <br>

            <div class="alert alert-danger" role="alert">
                名前を更新する前に、最新の情報をダウンロードしてください。
            </div>

            <button type="submit" class="btn btn-primary form-control"
                    style="margin-top: 25px">取り込み</button>

        </form>



    </div>

</div>

