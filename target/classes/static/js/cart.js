function addToCart(id){

fetch("/cart/add",{
method:"POST",
headers:{
"Content-Type":"application/x-www-form-urlencoded"
},
body:`id=${id}&quantity=1`
})
.then(res=>res.text())
.then(data=>{
alert("Đã thêm vào giỏ hàng");
});

}