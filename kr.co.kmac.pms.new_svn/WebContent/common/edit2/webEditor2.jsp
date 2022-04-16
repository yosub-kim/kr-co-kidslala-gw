<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Summernote</title>
  <link href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css" rel="stylesheet">
  <script src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
  <link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.css" rel="stylesheet">
  <script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.js"></script>
</head>
<body>
<script type="text/javascript">
jQuery.noConflict();
var j$ = jQuery;
</script> 

<script>
	jQuery(document).ready(function() {
		jQuery('#summernote').summernote({
			height: 500,
			focus : true,
			placeholder : '게시글을 입력해 주세요.',
			dialogsInBody: true,
			fontNames: ['나눔고딕','맑은 고딕','궁서','굴림체','굴림','돋음체','바탕체','Arial', 'Arial Black', 'Comic Sans MS', 'Courier New'],
	        toolbar: [
	                  ['fontname', ['fontname']],
	                  ['fontsize', ['fontsize']],
	                  ['style', ['bold', 'underline','strikethrough']],
	                  ['color', ['forecolor','color']],
		  			  ['table', ['table']],
					  ['para', ['ul', 'ol', 'paragraph']],
					  ['height', ['height']],
					  ['view', ['codeview']],
	                  ['link', ['linkDialogShow', 'unlink']]
	                ],
              popover: {
              	  image: [
              	    ['image', ['resizeFull', 'resizeHalf', 'resizeQuarter', 'resizeNone']],
              	    ['float', ['floatLeft', 'floatRight', 'floatNone']],
              	    ['remove', ['removeMedia']]
              	  ],
              	  link: [
              	    ['link', ['linkDialogShow', 'unlink']]
              	  ],
              	  table: [
              	    ['add', ['addRowDown', 'addRowUp', 'addColLeft', 'addColRight']],
              	    ['delete', ['deleteRow', 'deleteCol', 'deleteTable']],
              	  ],
              	  air: [
              	    ['color', ['color']],
              	    ['font', ['bold', 'underline', 'clear']],
              	    ['para', ['ul', 'paragraph']],
              	    ['table', ['table']],
              	    ['insert', ['link', 'picture']]
              	  ]
              	}         
		});
		jQuery('#summernote').summernote('foreColor', 'black');
	});
</script>
</body>
</html>