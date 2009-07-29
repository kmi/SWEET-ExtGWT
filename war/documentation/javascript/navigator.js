// show a navigator bar inside the page who use the "navigator" ID
function showNavigator() {
	var navigator = document.getElementById("navigator");
	var code = "<a class='linkable' href='index.html'>Index</a> | " +
			   "<a class='linkable' href='usecase.html'>&lt;-- Use case diagram</a><br /><br />";
	navigator.innerHTML = code;
}