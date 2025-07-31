let imageUrls = [];

let currentGrid = null;
let currentIndex = 0;
let slideshowInterval = null;

function showModalAtIndex(index) {
  currentIndex = index;
  PhotoGalleryLib.setModalImgSrc(imageUrls[currentIndex]);
  PhotoGalleryLib.openPresentationModal();
}

function showNextImage() {
  currentIndex = (currentIndex + 1) % imageUrls.length;
  PhotoGalleryLib.setModalImgSrc(imageUrls[currentIndex]);
}

function showPreviousImage() {
  currentIndex = (currentIndex - 1 + imageUrls.length) % imageUrls.length;
  PhotoGalleryLib.setModalImgSrc(imageUrls[currentIndex]);
}

function closeModal() {
  PhotoGalleryLib.closePresentationModal();
  if (slideshowInterval) {
    clearInterval(slideshowInterval);
    slideshowInterval = null;
  }
}

function setupModalHandlers() {
  PhotoGalleryLib.initModal(
    closeModal,
    showPreviousImage,
    showNextImage
  );
}

function setupImageClickHandlers() {
  PhotoGalleryLib.addImageClickHandlers(function(index) {
    showModalAtIndex(index);
  });
}

function setupSlideshowButton() {
  const btn = document.getElementById('start-slideshow');
  btn.addEventListener('click', function() {
    showModalAtIndex(0);
    if (slideshowInterval) clearInterval(slideshowInterval);
    slideshowInterval = setInterval(showNextImage, 1000);
  });
}

function ensureModalCreated() {
  if (!document.getElementById('presentationModal')) {
    PhotoGalleryLib.createModal();
    setupModalHandlers();
  }
}

function afterGridRender() {
  setupImageClickHandlers();
  ensureModalCreated();
}

function renderGrid(size) {
  const container = document.getElementById('galleryContainer');
  container.innerHTML = '';
  if (imageUrls.length === 0) return;
  const table = PhotoGalleryLib.generateGrid(imageUrls, size);
  if (table) {
    table.id = 'imagesGrid';
    container.appendChild(table);
    afterGridRender();
  }
}

function fetchImageUrls() {
  fetch('https://raw.githubusercontent.com/ubc-vsp25/ubc-vsp25.github.io/main/images')
    .then(response => {
      if (!response.ok) throw new Error('Network response was not ok');
      return response.json();
    })
    .then(data => {
      console.log('Fetched image URLs:', data);
      imageUrls = data;
      renderGrid('small');
    })
    .catch(error => {
      console.error('Error fetching image URLs:', error);
    });
}

PhotoGalleryLib.onSizeClassChange(function(size) {
  console.log(size);
  if (imageUrls.length > 0) {
    renderGrid(size);
  }
});

document.addEventListener('DOMContentLoaded', function() {
  setupSlideshowButton();
  fetchImageUrls();
});
