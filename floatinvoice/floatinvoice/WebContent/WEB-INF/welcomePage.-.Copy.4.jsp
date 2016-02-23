<!DOCTYPE html>
<html lang="en" >
<head>
  <title>Float Invoice - Accelerate Cashflow</title>
  <meta charset="utf-8">
  <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
  <style>
table, th , td  {
  border: 1px solid grey;
  border-collapse: collapse;
  padding: 5px;
}
table tr:nth-child(odd) {
  background-color: #f1f1f1;
}
table tr:nth-child(even) {
  background-color: #ffffff;
}

</style>
  <script src="js/jquery.min.js"></script>
  <script src="js/bootstrap.min.js"></script>
  <script src="js/angular.min.js"></script>
  <script src="js/angular-route.min.js"></script>
	
  
</head>
<body>

<div class="container">
  <div>
  
    <img src = "img/logo.jpg" height=100/>
    <p> Welcome ${acronym}</p>
  </div>
  <ul class="nav nav-tabs nav-pills nav-justified">
    <li class="active"><a data-toggle="pill" href="#upload">Upload</a></li>
    <li><a data-toggle="pill" href="#active">Floated</a></li>
    <li><a data-toggle="pill" href="#funded">Pending</a></li>
    <li><a data-toggle="pill" href="#accounts">Funded</a></li>
    <li><a data-toggle="pill" href="#inactive">Paid</a></li>
    <li><a data-toggle="pill" href="#inactive">Inactive</a></li>
  </ul>
  <br>
  <div class="tab-content">
    <div id="upload" class="tab-pane fade in active ">
	  <jsp:include page="upload.jsp" />
  </div>
  <div id="active" class="tab-pane fade" >
      <jsp:include page="SMEInvoiceView.jsp" />
  </div>
    <div id="funded" class="tab-pane fade">
      <jsp:include page="financierInvoiceView.jsp" />
    </div>
    <div id="accounts" class="tab-pane fade">
      <p>Accounts</p>
    </div>
    <div id="inactive" class="tab-pane fade">
      <p>Inactive</p>
    </div>
  </div>
</div>
    <script src="js/fileUpload.js"></script>
</body>
</html>
