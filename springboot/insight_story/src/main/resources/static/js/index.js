

function goBack() {
        window.history.back();
};

function goToSelectContent(id){
        console.log(id);
        const url = "/content/"+id;
        window.location.href = url;
        
}