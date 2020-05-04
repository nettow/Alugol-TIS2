window.addEventListener('scroll', function() {
	var element = document.querySelector('#btnEntrarApr');
	var pos = element.getBoundingClientRect();

    if( !(pos.top >= 0) )
        $('.navBar').css('display', 'flex');
    else $('.navBar').css('display', 'none');
})

$('.navBar a, #btnEntrarNav a, #btnEntrarApr a').on('click', function(e) {
    if(this.hash !== '') {
        e.preventDefault();

        const hash = this.hash;

        $('html, body').animate(
            {
                scrollTop: $(hash).offset().top
            }, 600);
    }
});