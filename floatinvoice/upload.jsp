

<html lang="en" class="no-js">

<head>
	<meta charset="utf-8">
	<title>Drag and Drop File Uploading</title>	
	<meta name="viewport" content="width=device-width,initial-scale=1" />
	<link rel="stylesheet" href="css/main.css" />
	<link rel="stylesheet" href="css/fileUpload.css" />

	<link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Roboto:300,300italic,400" />
	
<script>(function(e,t,n){var r=e.querySelectorAll("html")[0];r.className=r.className.replace(/(^|\s)no-js(\s|$)/,"$1js$2")})(document,window,0);</script>

  <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">

</head>

<body>

			<div class="container2" role="main">
			
			<form method="post" action="/floatinvoice/invoice/upload?acro=${acronym}" enctype="multipart/form-data" novalidate class="box">

				
				<div class="box__input">
					<svg class="box__icon" xmlns="http://www.w3.org/2000/svg" width="50" height="43" viewBox="0 0 50 43"><path d="M48.4 26.5c-.9 0-1.7.7-1.7 1.7v11.6h-43.3v-11.6c0-.9-.7-1.7-1.7-1.7s-1.7.7-1.7 1.7v13.2c0 .9.7 1.7 1.7 1.7h46.7c.9 0 1.7-.7 1.7-1.7v-13.2c0-1-.7-1.7-1.7-1.7zm-24.5 6.1c.3.3.8.5 1.2.5.4 0 .9-.2 1.2-.5l10-11.6c.7-.7.7-1.7 0-2.4s-1.7-.7-2.4 0l-7.1 8.3v-25.3c0-.9-.7-1.7-1.7-1.7s-1.7.7-1.7 1.7v25.3l-7.1-8.3c-.7-.7-1.7-.7-2.4 0s-.7 1.7 0 2.4l10 11.6z"/></svg>
					<input type="file" name="file" id="file" class="box___file"  />



					<label for="file"><strong>Choose a file</strong><span class="box__dragndrop"> or drag it here</span>.</label>
					<button type="submit" class="box__button">Upload</button>
					
				</div>

				
				<div class="box__uploading">Uploading&hellip;</div>
				<div class="box__success">Done! <a href="" class="box__restart" role="button">Upload more?</a></div>
				<div class="box__error">Error! <span></span>. <a href="" class="box__restart" role="button">Try again!</a></div>
			</form>

		</div>



<script src="js/fileUpload.js"></script>

</body>

</html> 