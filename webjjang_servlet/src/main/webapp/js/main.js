/**
 * 
 */

 $(function(){
	$(".datarow").click(function(){
		if($(this).hasClass("img")){
			let no=$(this).find(".no").val();
			console.log(no);
			location="/image/view.do?no="+no;
		}else if($(this).hasClass("board")){
			let no=$(this).find(".no").text();
			console.log(no);
			location="/board/view.do?no="+no+"&inc=1";
		}
	});
 });