<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>


<form method="post" action="/user/save-temperature" class="pl-3 m-4" style="font-size: 13px">
    <div class="pt-3">
        <h4>体調の入力</h4>
    </div>
    <p style="color: red">${error}</p>
    <div class="form-group pt-3">
        <input type="text" class="form-control" name="lastUsed"
               value="${time}" disabled />
    </div>
    <div class="form-group pt-3">
        <label for="userId">社員番号</label>
        <input type="text" class="form-control" name="id" id="userId" maxlength="6" value="${id}" disabled />
        <input type="hidden" class="form-control" name="userId" value="${id}"  />
    </div>
    <div class="form-group pt-3">
        <label for="name">氏名</label>
        <input type="text" class="form-control" name="name" id="name" maxlength="50" value="${name}" disabled />
    </div>
    <p>本日の体温を入力してください。</p>
    <p>なお、37.5℃以上ある場合は所属部門長へ連絡の上、欠席の手続きをしてください。</p>
    <input type="number" name="temperature" maxlength="4" id="temperature" step=0.01 required/>
    <label
        for="temperature"> &nbsp;&nbsp;℃</label>
    <p>風邪症状（発熱または熱感や悪寒、咳痰などの上気道症状、咽疼痛、鼻汁や鼻閉、倦怠感、関節痛、下痢、腹痛、</p>
    <p>
        吐き気、嘔吐）の有無を入力してください。
    </p>
    <p>風症状がある場合は 所属部門長へ連絡の上、欠席の手続きをしてください。</p>
    <select class="form-control-sm" name="symtoms" required>
        <option value="0">無い</option>
        <option value="1">有</option>
    </select>

    <button type="submit" class="btn btn-primary form-control"
            style="margin-top: 25px">登録</button>


</form>

