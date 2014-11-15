/**
 * get the quizes list from the quiz web service and display them on the html
 * page
 */

(function() {
	
	function Quizes(config){
		this.template=config.template;
		this.container=config.container;
		this.url=config.url;
		this.fetch();
	}
	
	Quizes.prototype={
			attachTemplate:function(){
				var self=this;
				var template=Handlebars.compile(this.template);
				 Handlebars.registerHelper('log', function(context) {
		                return console.log(context);
		            });
				this.container.append(template(this.items));
				this.events();
			},
			fetch:function(){
				var self=this;
				$.getJSON(this.url,function(data){
					/* var jsonList=JSON.parse(data.quizes);*/
					self.items=$.map(data.quiz,function(item){
						return {
							id:item.id,
							name:item.name
						}
					});
					self.attachTemplate();
				});
			},
			events:function(){
				this.container.find("a").on("click",function(e){
					/*e.preventDefault();*/
					
				});
			}
	};
	
	
	var rootURL = "http://localhost:8080/QuizAppWorkProject5/rest/hello";

     var quizes=new Quizes({
    	 url:rootURL,
    	 template:$(".quizesTemplate").html(),
    	 container:$("#quizesList")
     });
	
/*	function findAll() {
		console.log('findAll');
		$.ajax({
			type : 'GET',
			url : rootURL,
			dataType : "json", // data type of response
			success : renderList
		});
	}

	function renderList(data) {
		// JAX-RS serializes an empty list as null, and a 'collection of one' as
		// an object (not an 'array of one')
		var list = data == null ? []:data;
        var jsonList=JSON.parse(data.quizes);
		$('#quizesList li').remove();
		$.each(jsonList, function(index, quiz) {
			
			$('#quizesList').append("<li><a href='quiz.html?id=" + quiz.id + "'>"+ quiz.name + "</a></li>");
		});
	}
	
	findAll();*/
})();
