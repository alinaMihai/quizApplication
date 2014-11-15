$(document).ready(
		function() {

			function Quiz(config) {
				this.template = config.template;
				this.container = config.container;
				this.url = config.url;
				this.fetch();
			}

			Quiz.prototype = {
				attachTemplate : function() {
					var self = this;
					var template = Handlebars.compile(this.template);
					Handlebars.registerHelper('log', function(context) {
						return console.log(context);
					});
					this.container.append(template(this.items));
					this.events();
				},
				fetch : function() {
					var self = this;
					$.getJSON(this.url, function(data) {
						/*var jsonQuiz = JSON.parse(data.quiz);*/
						self.items = {
								id : data.id,
								name : data.name,
								question:data.questions
							
							};
						self.attachTemplate();
					});
				},
				events : function() {
					this.container.find("a").on("click", function(e) {
						/* e.preventDefault(); */

					});
				}
			};

			function getUrlVars() {
				var vars = [], hash;
				var hashes = window.location.href.slice(
						window.location.href.indexOf('?') + 1).split('&');
				for (var i = 0; i < hashes.length; i++) {
					hash = hashes[i].split('=');
					vars.push(hash[0]);
					vars[hash[0]] = hash[1];
				}
				return vars;
			}
			var quizId = getUrlVars()["id"];
			var rootURL = "http://localhost:8080/QuizAppWorkProject5/rest/hello";
           console.log(rootURL+"/"+quizId);
			var quizes = new Quiz({
				url : rootURL + "/" + quizId,
				template : $(".quizTemplate").html(),
				container : $("#quizForm")
			});

		});