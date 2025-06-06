//API GETAdd commentMore actions
fetch('http://localhost:8080/api/imagens', {
    method: 'GET',
    headers: {
        'Content-Type': 'application/json'
    },
})
    .then(response => response.json())
    .then(data => {
        addlinha(data);
        atualizarGaleria(data);
    })
    .catch(error => {
        Swal.fire("Erro ao carregar dados", "Não foi possível carregar os dados da API.", "error");
    });


//Adicionar Linha na Tabela
function addlinha(dadosAPI) {
    const tabela = document.getElementById('tabelaCorpo');
    dadosAPI.forEach(element => {
        const linha = document.createElement('tr');
        //Adicionando HTML
        linha.innerHTML = `
          <tr>
            <td class="px-4 py-2 id-col">${element.id}</td>
            <td class="px-4 py-2 nome-col">${element.nome}</td>
            <td class="px-4 py-2 url-col">${element.url}</td>
            <td class="px-4 py-2 flex gap-2">
            <button class="bg-red-500 text-white px-2 py-1 rounded" onclick="remover(this)">Remover</button>
            <button class="bg-blue-500 text-white px-2 py-1 rounded" onclick="Editar(this)">Editar</button>
            </td>
        `;

        //flex justify-center gap-3
        tabela.appendChild(linha);
    });
}


document.getElementById('formCadastro').addEventListener('submit', function (event) {
    event.preventDefault(); // evita o reload da página
    cadastrar(); // chama a função normalmente
});

//Cadastrar Novas pessoas do formulario
function cadastrar() {
    const nome = document.getElementById('nome').value;
    const url = document.getElementById('url').value;
    if (nome && url) {
        //Limpando os campos
        document.getElementById('nome').value = "";
        document.getElementById('url').value = "";

        //API POST  
        
        fetch('http://localhost:8080/api/imagens', {
            method: 'POST', 
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ "nome": nome, "url": url })
        })
            .then(response => response.json())
            .then(data => {
                console.log("Resposta da API:", data);
                this.addlinha([data]);
                atualizarGaleria();
                Swal.fire({
                    icon: 'success',
                    title: 'Sucesso!',
                    text: 'Cadastro feito com sucesso'
                });
            })
            .catch(error => {
                console.error("Erro ao enviar dados:", error);
                Swal.fire({
                    icon: 'error',
                    title: 'Erro!',
                    text: 'Ocorreu um erro ao cadastrar'
                });
            });
    } else {
        Swal.fire({
            icon: 'error',
            title: 'Erro!',
            text: 'Falta dados para cadastar'
        });
    }
}

async function remover(dadosbotao) {
      try {
        const resposta = await Swal.fire({ 
          icon: 'question',
          title: 'Você tem certeza?',
          showCancelButton: true,
          confirmButtonText: 'Sim',
          cancelButtonText: 'Não'
        });
        const linha = dadosbotao.closest('tr'); 
        const id = linha.querySelector('td').textContent.trim(); 
        if(resposta.isConfirmed){ 
            const deletar = await fetch(`http://localhost:8080/api/imagens/${id}`, { 
              method: 'DELETE',
            });
            if(deletar.ok){ 
              linha.remove(); 
              atualizarGaleria();
              Swal.fire('Confirmado!', '', 'success');
            }else{
              Swal.fire('ERRO!', '', 'Nao foi possivel deletar o aluno');
            }
          
        }else{
          Swal.fire('Cancelado!', '', '')
        }
      } catch (error) {
         console.log("Erro ao deletar o aluno")
      }
}

async function Editar(dadosbotao) {
    const linhaEditar = dadosbotao.closest('tr');
    const id = linhaEditar.querySelector('.id-col').textContent.trim();
    const nome = linhaEditar.querySelector('.nome-col').textContent.trim();
    const url = linhaEditar.querySelector('.url-col').textContent.trim();


    const { value } = await Swal.fire({
        title: "Editar",
        html: `
            <p>Nome</p>
            <input value=${nome} id="swal-input1" class="swal2-input">
            <p>URL</p>
            <input value=${url} id="swal-input2" class="swal2-input">
        `,
        focusConfirm: false,
        preConfirm: () => {
            return [
                document.getElementById("swal-input1").value,
                document.getElementById("swal-input2").value
            ];
        }
    });

    fetch(`http://localhost:8080/api/imagens/${id}`, {
        method: 'PUT',
        body: JSON.stringify({ "nome": value[0], "url": value[1] }),
        headers: {
            'Content-Type': 'application/json'
        }
    }).then((res) => {
        if (res.ok) {
            Swal.fire('Confirmado!', '', 'success');
            linhaEditar.querySelector('.nome-col').textContent = value[0];
            linhaEditar.querySelector('.url-col').textContent = value[1];
            atualizarGaleria();
            return;
        }

        throw new Error('Erro ao editar o item');
    }).catch((err) => {
        Swal.fire('Erro ao editar!', '', 'error');
    });
}

function colocarImagens(dadosAPI) {
    const galeria = document.getElementById('galeria');
    galeria.innerHTML = '';

    dadosAPI.forEach(element => {
        const card = document.createElement('div');
        card.className = "bg-white shadow-md rounded p-2 flex flex-col items-center";

        card.innerHTML = `
            <img src="${element.url}" alt="${element.nome}" class="w-full h-40 object-cover rounded mb-2" onerror="this.style.display='none'">
            <p class="text-center font-semibold">${element.nome}</p>
        `;
        galeria.appendChild(card);
    });
}

function atualizarGaleria() {
    fetch('http://localhost:8080/api/imagens')
        .then(response => response.json())
        .then(data => {
            colocarImagens(data);
        })
        .catch(error => {
            console.error("Erro ao atualizar galeria:", error);
        });
}
