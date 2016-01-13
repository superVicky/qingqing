/*
用途：检查输入字符串是否为空或者全部都是空格
输入：str
返回：
如果全是空返回true,否则返回false
*/
function isNull(str) {
	if (str == "") return true;
	var regu = "^[ ]+$";
	var re = new RegExp(regu);
	return re.test(str);
} 

/*
用途：检查输入手机号码是否正确
输入：
s：字符串
返回：
如果通过验证返回true,否则返回false
*/
function checkMobile(s) {
	var regu = /^[1][0-9]{10}$/;
	var re = new RegExp(regu);
	if (re.test(s)) {
	    return true;
	} else {
	    return false;
	}
} 
//点击切换验证码
var count=0;
$("#yzmImg").click(function(){
	count++;
	$(this).attr("src","../captcha/index?temp="+count);
});