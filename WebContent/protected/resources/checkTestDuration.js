(function() {

	var progressBar = $("#progressBar");
	var timeLimitElement = $("#timeLimit");
	var start = new Date();
	var maxTime = durationToSeconds * 1000;
	var timeoutVal = Math.floor(maxTime / 100);

	function updateProgress(percentage) {
		progressBar.css("width", percentage + "%");
		timeLimitElement.text(percentage + "%");
	}

	function animateUpdate() {
		var now = new Date();
		var timeDiff = now.getTime() - start.getTime();
		var perc = Math.round((timeDiff / maxTime) * 100);

		if (perc <= 100) {
			updateProgress(perc);
			setTimeout(animateUpdate, timeoutVal);
		}
	}

	function createDialog(message) {
		var newDiv = $(document.createElement("div"));
		newDiv.html(message);
		newDiv.dialog({
			dialogClass : "no-close",
			buttons : [ {
				text : "OK",
				click : function() {
					$(this).dialog("close");
				}
			} ]
		});
	}

	window.onload = function() {

		var duration = parseInt(timeLimitElement.text());
		var durationToSeconds = duration * 60;

		var counter = 1;
		animateUpdate();

		var counter = setInterval(
				function() {
					counter++;
					console.log(counter);
					// notify the user when there are 5 minutes left
					if (counter == durationToSeconds - 300) {
						createDialog("You have 5 more minutes. Please prepare to submit the quiz");
						console
								.log("You have 5 more minutes. Please prepare to submit the quiz");
					} else if (counter == durationToSeconds) {
						createDialog("Time is up. The quiz will be automatically submited");
						document.forms["quizForm"].submit();
						clearInterval(counter);
					}

				}, 1000);

		$("quizForm").submit(function(ev) {
			clearInterval(counter);
		});

	};

})();
