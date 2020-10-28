<form method="POST" action="/admin/user-saved">

    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />

    <div class="form-group">
        <label for="title">User ID</label>
        <input type="text" name="userId" class="form-control" id="title"  placeholder="Enter ">

    </div>

    <div class="form-group">
        <label for="title">Full Name </label>
        <input type="text" name="fullName" class="form-control" id="title"  placeholder="Enter ">

    </div>
    <div class="form-group">
        <label for="title">Full Name In Kata </label>
        <input type="text" name="fullNameInKata" class="form-control" id="title"  placeholder="Enter ">

    </div>
    <div class="form-group">
        <label for="title">Department </label>
        <input type="text" name="department" class="form-control" id="title"  placeholder="Enter ">

    </div>
    <div class="form-group">
        <label for="title">Email </label>
        <input type="email" name="email" class="form-control" id="title"  placeholder="Enter ">

    </div>

    <div class="form-group">
        <label for="Status"> Role </label>
        <select class="form-control form-control" name="admin" id="Status">
            <option value="TRUE" >Admin</option>
            <option value="FALSE" >Not Admin</option>
        </select>
    </div>



    <button type="submit" class="btn btn-primary">Submit</button>
</form>