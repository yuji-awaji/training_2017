appear_flag = false;

$(function() {
	$('#enemy_area').toggle(false);
	//対戦待機ダイアログ
	if (appear_flag == false) {
		$('#wait_dialog').dialog({
			title : "対戦待機",
			closeOnEscape : false,//エスケープキーでとじないように
			modal : true,
			resizable : false,
			dialogClass : "no-close",//×ボタンを消す処理
			buttons : [ {
				text : "やめる",
				id : "open_button",
				click : function() {
					$(this).dialog('close');
					$('#cancel_confirmation_dialog').dialog('open');
				}
			} ]
		});
		//ポーリング処理で対戦相手が来たかどうか調べる
		$(function() {
			polling();
		});
	}
	//対戦者が現れた時のダイアログ
	$('#battle_confirmation_diaglog').dialog({
		title : "対戦開始",
		closeOnEscape : false,//エスケープキーでとじないように
		modal : true,
		autoOpen : false,
		resizable : false,
		dialogClass : "no-close",//×ボタンを消す処理
		buttons : [ {
			text : "はじめる",
			id : "open_button",
			click : function() {
				$(this).dialog('close');
				//toggleを使って反転
				$('#enemy_area').toggle(true);
			}
		} ]
	});

	//キャンセル確認ダイアログ
	$('#cancel_confirmation_dialog').dialog({
		autoOpen : false,
		dialogClass : "no-close",//×ボタンを消す処理
		closeOnEscape : false,//エスケープキーでとじないように
		title : "対戦をキャンセルします",
		resizable : false,
		modal : true,
		buttons : {
			"OK" : function() {
				$(this).dialog('close');

				//ルームの状態を変更する。
				var data = {
					roomState : "2",
				};
				$.ajax({
					type : "POST",
					url : "/endgame",
					data : JSON.stringify(data),
					contentType : 'application/json',
					dataType : "json",
				});
				window.location.href = "/redirectmenu";

			},
			"キャンセル" : function() {
				$(this).dialog('close');
				$('#wait_dialog').dialog('open');
			}
		}
	});

	//退出ダイアログの処理
	$('#leaving_dialog').dialog({
		autoOpen : false,
		dialogClass : "no-close",//×ボタンを消す処理
		closeOnEscape : false,//エスケープキーでとじないように
		title : "退出します",
		resizable : false,
		modal : false,
		buttons : {
			"OK" : function() {
				$(this).dialog('close');
				window.location.href = "/redirectmenu";
			},
			"キャンセル" : function() {
				$(this).dialog('close');
			}
		}
	});

	//スキップボタンが押された時の処理
	$('#leaving_button').attr("disabled", true);
	//スキップボタンの非表示
	//$('#skip_button').attr("disabled",true);
	$('#leaving_button').click(function() {
		$('#leaving_dialog').dialog('open');
	});
});

//1秒後に変える モック用処理
//window.setTimeout("battle_start()", 3000);

//ポーリング
function mySleep(time) {
	return (function() {
		var dfd = $.Deferred();
		setTimeout(function() {
			dfd.resolve();
		}, time * 1000);
		return dfd.promise();
	});
}

// 5秒毎にポーリング
function polling() {
	var data = {
		roomState : "0",
	};

	$.ajax({
		url : "/checkroomstate",
		type : "POST",
		data : JSON.stringify(data),
		contentType : 'application/json',
		dataType : "json",
	}).then(function(json_data) {
		// 取得したデータの内容によってDeferredの状態を変更する
		var roomState = json_data.roomState;
		var userNickname = json_data.userNickname;
		var encoded = json_data.userImage
		var fileType = json_data.imageFileType;

		var d = $.Deferred();
		if (roomState == 0) {
			//doneの処理、ポーリング
			d.resolve();
		} else if (roomState == 1) {
			//failの処理、対戦相手がそろったので対戦確認ダイアログの表示
			changeUserInfo(userNickname, encoded, fileType);
			d.reject();
			//5/25指摘：roomStateが2の時の処理を記述
		} else if (roomState == 2) {
			alert("エラー：メニューに戻ります");
			window.location.href = "/redirectmenu";
		}

		// Promiseを返す
		return d.promise();
	}).then(mySleep(5)).done(polling).fail(function() {
		battle_start();
	});
}

function changeUserInfo(userNickname, encoded, fileType) {
	$("#enemy_nickname").after(userNickname);
	var urlstr = 'data:' + fileType + ';base64,' + encoded;
	var img = new Image();
	img.src = 'data:' + fileType + ';base64,' + encoded;
	img.id = 'enemy_img';
	$('#enemy_img_frame').append(img);

	//$("#myself_img").after(th:src="'data:image/'+${filetype}+';base64,'+${encoded}");
}

function Base64ToImage(base64img, callback) {
	var img = new Image();
	img.onload = function() {
		callback(img);
	};
	img.src = base64img;
}

//対戦者が現れた時のダイアログ
function battle_start() {
	$('#wait_dialog').dialog('close');
	$('#battle_confirmation_diaglog').dialog('open');
	$('#cancel_confirmation_dialog').dialog('close');
}
