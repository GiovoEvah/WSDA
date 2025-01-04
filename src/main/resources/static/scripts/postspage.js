document.addEventListener("DOMContentLoaded", function () {
    loadPosts();
    setupModalEvents();

    // Nascondere il modale all'avvio della pagina
    const modal = document.getElementById("postModal");
    modal.style.display = "none";
    modal.setAttribute('hidden', 'true');
});
let offset = 0;

function loadPosts() {
    fetch('/export/xml')
        .then(response => response.text())
        .then(data => {
            const xmlDoc = new window.DOMParser().parseFromString(data, "text/xml");
            displayPosts(xmlDoc);
        })
        .catch(error => console.error("Errore nel caricamento del file XML:", error));
}

function displayPosts(xmlDoc) {
    const postContainer = document.getElementById("postContainer");
    const posts = xmlDoc.getElementsByTagName("post");

    for (let post of posts) {
        const id = post.getElementsByTagName("id")[0].textContent;
        const autoreNome = post.getElementsByTagName("nome")[0].textContent;
        const autoreCognome = post.getElementsByTagName("cognome")[0].textContent;
        const dataOra = post.getElementsByTagName("creatoIl")[0].textContent;
        const testo = post.getElementsByTagName("contenuto")[0].textContent;

        const autore = `${autoreNome} ${autoreCognome}`;
        const timeAgo = calculateTimeAgo(dataOra);

        const postElement = document.createElement("li");
        postElement.classList.add("post");

        postElement.innerHTML = `
            <div class="post-header">
                <textarea id="id_post_${id}" hidden="true">${id}</textarea>
                <h3>${autore}</h3>
                <!--<span class="time">${timeAgo}</span>-->
            </div>
            <p>${testo}</p>
            <button id="view-button_${id}" class="view-button">Visualizza</button>
        `;

        // Aggiungi evento click al pulsante "Visualizza"
        const viewButton = postElement.querySelector(".view-button");
        viewButton.addEventListener("click", () => {
            redirectToPostPage(post); // Passa il post corrente come argomento
        });

        postContainer.appendChild(postElement); // Aggiungi il post al container
    }
}


function redirectToPostPage(post) {
    // Dati del post
    offset = 1;
    const postId = post.getElementsByTagName("id")[0].textContent;
    const postTitle = `${post.getElementsByTagName("nome")[0].textContent} ${post.getElementsByTagName("cognome")[0].textContent}`;
    const postContent = post.getElementsByTagName("contenuto")[0].textContent;
    const comments = post.getElementsByTagName("commento");

    // Aggiorna la modale con i dettagli del post
    const modal = document.getElementById("postDetailsModal");
    document.getElementById("postId").textContent = postId;
    document.getElementById("postDetailsTitle").textContent = postTitle;
    document.getElementById("postDetailsContent").textContent = postContent;

    // Elenca i commenti
    const commentsList = document.getElementById("postCommentsList");
    commentsList.innerHTML = ""; // Svuota la lista dei commenti
    Array.from(comments).forEach((comment) => {
        const commentUser = `${comment.getElementsByTagName("nome")[0].textContent} ${comment.getElementsByTagName("cognome")[0].textContent}`;
        const commentContent = comment.getElementsByTagName("testo")[0].textContent;

        const commentElement = document.createElement("li");
        commentElement.textContent = `${commentUser}: ${commentContent}`;
        commentsList.appendChild(commentElement);
    });

    // Mostra la modale
    modal.style.display = "flex";
    modal.style.opacity = "1";


    // Chiudi la modale
    const closeModalButton = document.getElementById("closePostDetailsModal");
    closeModalButton.addEventListener("click", () => {
        modal.style.display = "none";
        modal.style.opacity = "0";
    });
}

function calculateTimeAgo(dataOra) {
    const postDate = new Date(dataOra);
    const currentDate = new Date();

    if (isNaN(postDate.getTime())) {
        console.error("Data non valida:", dataOra);
        return "Data non valida";
    }

    if (postDate > currentDate) {
        console.error("Data futura:", dataOra);
        return "Data futura non valida";
    }

    const timeDifference = currentDate - postDate;

    const seconds = Math.floor(timeDifference / 1000);
    const minutes = Math.floor(timeDifference / (1000 * 60));
    const hours = Math.floor(timeDifference / (1000 * 60 * 60));
    const days = Math.floor(timeDifference / (1000 * 60 * 60 * 24));
    const weeks = Math.floor(timeDifference / (1000 * 60 * 60 * 24 * 7));
    const months = Math.floor(timeDifference / (1000 * 60 * 60 * 24 * 30.44));
    const years = Math.floor(timeDifference / (1000 * 60 * 60 * 24 * 365.25));

    if (seconds < 60) {
        return `${seconds} secondi fa`;
    }

    if (minutes < 60) {
        return `${minutes} minuti fa`;
    } else if (hours < 24) {
        return `${hours} ore fa`;
    } else if (days < 7) {
        return `${days} giorni fa`;
    } else if (weeks < 4) {
        return `${weeks} settimane fa`;
    } else if (months < 12) {
        return `${months} mesi fa`;
    } else {
        return `${years} anni fa`;
    }
}

function setupModalEvents() {
    const openModalButton = document.getElementById("openModalButton");
    const closeModalButton = document.getElementById("closeModalButton");
    const modal = document.getElementById("postModal");

    // Mostra il modale
    openModalButton.addEventListener('click', () => {
        modal.style.display = 'flex'; // Cambia display da 'none' a 'flex'
        modal.style.opacity = '1';   // Assicura che la modale sia visibile
    });

    // Nascondi il modale
    closeModalButton.addEventListener('click', () => {
        modal.style.display = 'none'; // Nasconde la modale
        modal.style.opacity = '0';    // (Facoltativo) Reset dell'opacità
    });

    // Chiudi il modale cliccando fuori dal contenuto
    modal.addEventListener('click', (event) => {
        if (event.target === modal) {
            modal.style.display = 'none';
            modal.style.opacity = '0';
        }
    });
}

function loadMoreComments(offsetToApply, currentPostId){
    fetch(`/getPaged?postId=${currentPostId}&offset=${offsetToApply}`)
.then(response => response.json())
        .then(newComments => {
            const modalComments = document.getElementById('postCommentsList');
            if (newComments.length > 0) {
                newComments.forEach(comment => {
                    let commentLi = document.createElement('li');
                    commentLi.classList.add('comment');
                    commentLi.classList.add('loadedComment');
                    commentLi.innerHTML = `${comment.utente.nome} ${comment.utente.cognome} : ${comment.testo}`;
                    modalComments.appendChild(commentLi);
                });
                console.log(newComments);
                offset += 1;
            }
            else{
                alert("Non ci sono altri commenti da caricare.");
            }


        })
        .catch(error => console.error('Errore nel caricamento dei commenti:', error));

}

document.getElementById("loadMoreComments").addEventListener('click', (event) => {
    let postId = document.getElementById("postId").innerHTML;
    loadMoreComments(offset, postId);
});
