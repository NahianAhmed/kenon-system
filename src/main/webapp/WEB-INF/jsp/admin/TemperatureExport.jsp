
<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<div class="row">

    <div class="col-10 offset-1">
        <form method="post" action="/admin/user-temperature"  class="pl-3 m-4" style="font-size: 13px">

            <div class="pt-3">
                <h5>一覧の出力</h5>
            </div>
            <p style="color: red">${error}</p>


            <div class="form-group">
                <div class="pt-3">
                    <h5>出力時間 (選択日を含む過去5日間)</h5>
                </div>
                <input type="date" name="date" class="form-control" required>

            </div>

            <div class="form-group">
                <div class="pt-3">
                    <h5>出力部門 </h5>
                    <select class="form-control" name="department" required >

                       ${department}

                           <option value="all"> 全て </option>

                    </select>
                </div>

            </div>

            <br>



            <button type="submit" class="btn btn-primary form-control"
                    style="margin-top: 25px">出力</button>

        </form>



    </div>

</div>

