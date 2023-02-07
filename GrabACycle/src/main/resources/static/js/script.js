console.log("this is js file");

const toggleSidebar = () => {
  if ($(".sidebar").is(":visible")) {
    //true
    //band karna hai
    $(".sidebar").css("display", "none");
    $(".content").css("margin-left", "0%");
  } else {
    //false
    //show karna hai
    $(".sidebar").css("display", "block");
    $(".content").css("margin-left", "20%");
  }
}


(function () {
  'use strict'

  // Fetch all the forms we want to apply custom Bootstrap validation styles to
  var forms = document.querySelectorAll('.needs-validation')

  // Loop over them and prevent submission
  Array.prototype.slice.call(forms)
    .forEach(function (form) {
      form.addEventListener('submit', function (event) {
        if (!form.checkValidity()) {
          event.preventDefault()
          event.stopPropagation()
        }

        form.classList.add('was-validated')
      }, false)
    })
})()




function bookCyclefun(cid)
		{
			
			swal({
				  title: "Confirm Booking",
				  text: "Are you sure",
				  icon: "warning",
				  buttons: true,
				  dangerMode: true,
				})
				.then((willBook) => {
				  if (willBook) {
				   
					  
					  window.location="/buyer/book/"+cid;
					  
				  } else {
				    swal("Cycle not booked  !!");
				  }
				});
			
		}

const search = () => {
  // console.log("searching...");

  let query = $("#search-input").val();

  if (query == "") {
    $(".search-result").hide();
  } else {
    //search
    console.log(query);

    //sending request to server

    let url = `http://localhost:8080/search/${query}`;

    fetch(url)
      .then((response) => {
        return response.json();
      })
      .then((data) => {
        //data......
        // console.log(data);

        let text = `<div class='list-group'>`;

        data.forEach((cycle) => {
          text += `<a href='/${cycle.cid}/cycle' class='list-group-item list-group-item-action'> ${cycle.name}  </a>`;
        });

        text += `</div>`;

        $(".search-result").html(text);
        $(".search-result").show();
      });
  }
};


