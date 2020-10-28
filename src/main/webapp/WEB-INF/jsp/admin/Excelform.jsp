
<form method="POST" action="/admin/upload" enctype="multipart/form-data" >

    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />

    <div class="form-group">

        <label for="title">Excel File</label>
        <input type="file" name="file" class="form-control" id="title"  placeholder="Enter Title">

    </div>


    <button type="submit" class="btn btn-primary">Submit</button>

</form>