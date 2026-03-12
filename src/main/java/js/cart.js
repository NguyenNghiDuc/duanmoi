function addToCart(productId){

fetch("/add-to-cart/"+productId)

.then(response => {

alert("Đã thêm vào giỏ hàng")

})

}