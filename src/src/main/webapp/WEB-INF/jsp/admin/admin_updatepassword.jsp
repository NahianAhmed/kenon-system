<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<form action="/admin/password-update" method="post" class="pl-3">
    <div class="pt-3">
        <h4>パスワード変更</h4>
        <p style="color: red">${error}</p>
    </div>
    <div class="form-group pt-3">
        <label for="old_password">現在のパスワード</label>
        <input type="password" class="form-control" id="old_password" name="old_password" required />
    </div>
    <div class="form-group pt-1">
        <label for="password">パスワード</label>
        <input type="password" class="form-control" id="password" name="password"  required/>
    </div>
    <div class="form-group pt-1">

        <label for="re_password">パスワード（確認用）</label>
        <input type="password" class="form-control" name="re_password" id="re_password"  required/>
    </div>

    <button type="submit" class="btn btn-primary form-control">パスワード変更</button>

</form>