function send(){
    let msg = document.getElementById("msg").value

    if(msg.trim() === "") return

    let chatbox = document.getElementById("chatbox")
    chatbox.innerHTML += "<p><b>You:</b> "+msg+"</p>"

    fetch("/chat/api?msg="+encodeURIComponent(msg))
        .then(res => res.text())
        .then(data => {
            chatbox.innerHTML += "<p><b>Bot:</b> "+data+"</p>"
        })

    document.getElementById("msg").value = ""
}