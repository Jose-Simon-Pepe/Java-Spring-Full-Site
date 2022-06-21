//slider
let slideIndex = 1;
showSlides(slideIndex);

plusSlides = (number) => showSlides(slideIndex += number);

currentSlide = (number) => showSlides(slideIndex = number);

function showSlides (number) {
    let i;
    let slides = document.getElementsByClassName("slides");
    let dots = document.getElementsByClassName("dot");

    if (number > slides.length) {slideIndex = 1}

    if (number < 1) {slideIndex = slides.length}

    for (i = 0; i < slides.length; i++) {
        slides[i].style.display = "none";
    }

    for (i = 0; i < dots.length; i++) {
        dots[i].className = dots[i].className.replace(" dot-active", "");
    }

    slides[slideIndex-1].style.display = "block";
    dots[slideIndex-1].className += " dot-active";
}

setInterval(() => {
    currentSlide(slideIndex + 1)
}, 3000);