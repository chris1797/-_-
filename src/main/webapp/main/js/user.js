  var token = $("#_csrf").attr("content");
	var header = $("#_csrf_header").attr("content");
	var idMac = null;
	var checkcode = null;
	var emailMac = null;
	

	$(function() {
	    $(document).ajaxSend(function(e, xhr, options) {
	        xhr.setRequestHeader(header, token);
	    });
	});
	
	//=====================================로그인 체크=========================================
	function added() {
		
		
		var pw = document.getElementById('pwMac').value;
	    var SC = ["!","@","#","$","%"];
	    var check_SC = 0;

	    //비밀번호 유효성검사
	    if(document.getElementById('pwMac').value.search(/\s/) != -1) {
	    	alert('비밀번호에 빈 칸을 포함 할 수 없습니다.');
	    	return false;
	    }
	    if(pw.length < 6 || pw.length>16){
	        window.alert('비밀번호는 6글자 이상, 16글자 이하만 이용 가능합니다.');
	        document.getElementById('pwMac').value='';
	        return false;
	    }
	    for(var i=0;i<SC.length;i++){
	        if(pw.indexOf(SC[i]) != -1){
	            check_SC = 1; 
	        }
	    }
	    if(check_SC == 0){
	        window.alert('!,@,#,$,% 의 특수문자가 들어가 있지 않습니다.')
	        document.getElementById('pwMac').value='';
	        return false;
	    }
		
	    //비밀번화 확인 유효성검사
	    if(document.getElementById('pwMac2').value==''){
	    	alert('비밀번호 확인을 하세요.');
	    	return false;
	    }
	    if(document.getElementById('pwMac').value !='' && document.getElementById('pwMac2').value!=''){
	        if(document.getElementById('pwMac').value!=document.getElementById('pwMac2').value){
	            document.getElementById('check').innerHTML='비밀번호가 일치하지 않습니다.';
	            document.getElementById('check').style.color='red';
	            alert('비밀번호가 일치하지 않습니다.');
	            return false;
	        }
	    }
	    
	    //휴대폰 유효성검사
	    var phonenum = document.getElementById('phoneNumMac').value;
	    var regPhone = /^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$/;
	    if(!regPhone.test(phonenum)){
	     alert('잘못된 휴대폰 번호입니다.');
	     return false;    
	    }
		
	    //닉네임 유효성 검사
	    if(document.getElementById('nickNameMac').value.length > 11){
	        alert('닉네임은 10글자를 넘을 수 없습니다..');
	        return false;
	    }
	    
	    if(document.getElementById('nickNameMac').value==""){
			alert('닉네임은 필수 사항입니다.');
	        return false;
		}
	    
	    if(document.getElementById('nickNameMac').value.search(/\s/) != -1) {
	    	alert('닉네임은 빈 칸을 포함 할 수 없습니다.');
	    	return false;
	    }
	   
	    $.ajax({
			url:'/user/nicknameCheck',
			method:'post',
			cache:false,
			data: {nick:document.getElementById('nickNameMac').value},
			dataType:'json',
			success:function(res){
				if(res.result) {
					$.ajax({
						url:'/user/add',
						method:'post',
						cache:false,
						data:$('#added').serialize(),
					
						dataType:'json',
						success:function(res){
							alert(res.result ? '회원가입 성공':'회원가입 실패');
							location.href="/login/loginForm";
						},
						error:function(xhr,status, err){
							alert('다시 확인하세요.');
						}
					});
					return false;
				}else{
					alert('이미 있는 닉네임입니다.');
				}
			},
			error:function(xhr,status, err){
			}
		});
		return false;
	}(jQuery);

	//========================================인증 구간==========================================
		
		

	

	function idcheck() {
		
		//아이디 유효성검사
	    if(document.getElementById('idMac').value.search(/\s/) != -1) {
	    	alert('빈 칸을 포함 할 수 없습니다.');
	    	return false;
	    }
		
	    if(document.getElementById('idMac').value==""){
			alert('아이디를 입력해주세요.');
	        return false;
		}
	    
	    var userId = document.getElementById('idMac').value;
	    var regId = /^([0-9a-zA-Z]{8,15})$/;
	    if(!regId.test(userId)){
	     alert('아이디는 숫자,영어 8~15글자여야 합니다.');
	     return false;    
	    }
	    
		$.ajax({
			url:'/user/idcheck',
			method:'post',
			cache:false, 
			data:$('#idcheck').serialize(),
			dataType:'json',
			success:function(res){
			
				
				if(res.result) {
					alert('아이디 사용가능');
					//location.href="/user/addForm/" + res.id;
					var form = document.createElement('form'); // 폼객체 생성
					var objs;
					var hidden1;
					objs = document.createElement('input'); // 값이 들어있는 녀석의 형식
					objs.setAttribute('type', 'text'); // 값이 들어있는 녀석의 type
					objs.setAttribute('name', 'check'); // 객체이름
					objs.setAttribute('value', 1); //객체값
					form.appendChild(objs);
					hidden1=document.createElement('input'); //인풋태그 생성
			        hidden1.setAttribute('type', 'hidden'); //태그 type속성 주기
			        hidden1.setAttribute('name', '_csrf'); //태그 name속성 주기
			        hidden1.setAttribute('value', token); //태그 value속성 주기
			        form.appendChild(hidden1);
					form.setAttribute('method', 'post'); //get,post 가능
					form.setAttribute('action', "/user/addForm/"+res.id); //보내는 url
					document.body.appendChild(form);
					form.submit();
				} else {
					alert('중복된 아이디가 있습니다.');
				}
			},
			error:function(xhr,status, err){
				alert('에러:'+err);
			}
		});
		return false;
	}(jQuery);

	//메일 전송
	function sendMsg()
	{
		$.ajax({
			url:'/user/email_authentication',
			method:'post',
			cache:false,
			data:$('#emailcheck').serialize(),
			dataType:'json',
			success:function(res){
				alert(res.result ? '메일 전송 완료':'메일 전송 실패');
				checkcode = res.code;
				emailMac = res.emailMac;
				idMac = document.getElementById('id').value;
			},
			error:function(xhr,status, err){
				alert('에러:'+err);
			}
		});
		return false;
	}

	//메일 체크
	function checked()
	{
		//alert('[[${user.idMac}]]');
		var data= $('#checkcode').serialize();
		$.ajax({
			url:'/user/checkcode',
			method:'post',
			cache:false,
			data:$('#checked').serialize(),
			dataType:'json',
			success:function(res){
				if(res.code == checkcode) {
					//alert("인증 성공");
					//location.href="/user/addForm/"+idMac+"/"+emailMac;
					var form = document.createElement('form'); // 폼객체 생성
					var objs;
					var hidden1;
					objs = document.createElement('input'); // 값이 들어있는 녀석의 형식
					objs.setAttribute('type', 'text'); // 값이 들어있는 녀석의 type
					objs.setAttribute('name', 'check'); // 객체이름
					objs.setAttribute('value', 1); //객체값
					form.appendChild(objs);
					hidden1=document.createElement('input'); //인풋태그 생성
			        hidden1.setAttribute('type', 'hidden'); //태그 type속성 주기
			        hidden1.setAttribute('name', '_csrf'); //태그 name속성 주기
			        hidden1.setAttribute('value',token); //태그 value속성 주기
			        form.appendChild(hidden1);
					form.setAttribute('method', 'post'); //get,post 가능
					form.setAttribute('action', "/user/addForm/"+idMac+"/"+emailMac); //보내는 url
					document.body.appendChild(form);
					form.submit();
				} else {
					alert("인증 실패");
				}
			},
			error:function(xhr,status, err){
				alert('에러:'+err);
			}
		});
		return false;
	}(jQuery);

	function keyevent() {
		if(document.getElementById('pwMac').value !='' && document.getElementById('pwMac2').value!=''){
	        if(document.getElementById('pwMac').value==document.getElementById('pwMac2').value){
	            document.getElementById('check').innerHTML='비밀번호가 일치합니다.';
	            document.getElementById('check').style.color='blue';
	        }
	        else{
	            document.getElementById('check').innerHTML='비밀번호가 일치하지 않습니다.';
	            document.getElementById('check').style.color='red';
	        }
	    }
	}(jQuery);

	function keyevent2() {
	
		
		if(document.getElementById('nickNameMac').value==""){
			document.getElementById('check2').innerHTML='닉네임은 필수사항 입니다.';
	        document.getElementById('check2').style.color='red';
	        return false;
		}
		
		$.ajax({
			url:'/user/nicknameCheck',
			method:'post',
			cache:false,
			data: {nick:document.getElementById('nickNameMac').value},
			dataType:'json',
			success:function(res){
				if(res.result) {
					document.getElementById('check2').innerHTML='사용 가능한 닉네임';
			        document.getElementById('check2').style.color='blue';
				} else {
					document.getElementById('check2').innerHTML='이미 있는 닉네임입니다.';
		            document.getElementById('check2').style.color='red';
				}
			},
			error:function(xhr,status, err){
				alert('에러:'+err);
			}
		});
		return false;
	}(jQuery);
	
	
	
		//========================================회원탈퇴==========================================
		
		function deleted() {
		if(!confirm('삭제하면 복구할 수 없습니다. \n 정말로 삭제하시겠습니까?')) {
			return false;
		}
		
		
		$.ajax({
			url:'/user/removal',
			method:'delete',
			cache:false,
			data:$('#del').serialize(),
			dataType:'json',
			success:function(res){
				alert(res.result ? '삭제 성공':'삭제 실패');
				location.href="/home";
			},
			error:function(xhr,status, err){
				alert('에러:'+err);
			}
		});
		return false;
	}
//========================================회원탈퇴==========================================	
	
function updated() {
	
	var pw = document.getElementById('pwMac').value;
	var id = document.getElementById('idMac').value;
    var SC = ["!","@","#","$","%"];
    var check_SC = 0;

    if(pw.length < 6 || pw.length>16){
        window.alert('비밀번호는 6글자 이상, 16글자 이하만 이용 가능합니다.');
        document.getElementById('pwMac').value='';
        return false;
    }
    for(var i=0;i<SC.length;i++){
        if(pw.indexOf(SC[i]) != -1){
            check_SC = 1;
        }
    }
    if(check_SC == 0){
        window.alert('!,@,#,$,% 의 특수문자가 들어가 있지 않습니다.')
        document.getElementById('pwMac').value='';
        return false;
    }
	
    if(document.getElementById('pwMac').value !='' && document.getElementById('pwMac2').value!=''){
        if(document.getElementById('pwMac').value==document.getElementById('pwMac2').value){
            document.getElementById('check').innerHTML='비밀번호가 일치합니다.'
            document.getElementById('check').style.color='blue';
        }
        else{
            document.getElementById('check').innerHTML='비밀번호가 일치하지 않습니다.';
            document.getElementById('check').style.color='red';
            alert('비밀번호가 일치하지 않습니다.');
            return false;
        }
    }
	
	
	$.ajax({
		url:'/user/modify',
		method:'put',
		cache:false,
		data:$('#updated').serialize(),
		dataType:'json',
		success:function(res){
			alert(res.result ? '수정 성공':'수정 실패');
			location.href="/user/detail/"+id;
		},
		error:function(xhr,status, err){
			alert('에러:'+err);
		}
	});
	return false;
}

function keyevent() {
	if(document.getElementById('pwMac').value !='' && document.getElementById('pwMac2').value!=''){
        if(document.getElementById('pwMac').value==document.getElementById('pwMac2').value){
            document.getElementById('check').innerHTML='비밀번호가 일치합니다.'
            document.getElementById('check').style.color='blue';
        }
        else{
            document.getElementById('check').innerHTML='비밀번호가 일치하지 않습니다.';
            document.getElementById('check').style.color='red';
        }
    }
}
	