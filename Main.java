public class Main {
    public static void main(String[] args) {
        // 1. Criando os tipos de venda (Requisito A)
        Tipo porQuilo = new Tipo("Alimento", "Quilo");
        
        // 2. Criando um produto (Sua classe Produto corrigida)
        // Note: Se o colega não atualizou o construtor de Produto, isso vai dar erro.
        Produto p1 = new Produto("MarcaX", "123", 10.0, 50.0, porQuilo);

        // 3. Criando os usuários (Sua classe Funcionario)
        Funcionario gerente = new Funcionario("Pedrinho", "gerente", "123");
        Funcionario comum = new Funcionario("Joao", "funcionario", "456");

        // 4. Testando a ItemNota (Sua classe com integridade)
        // Se o código do seu colega tentar passar o preço aqui, o VS Code vai acusar erro.
        ItemNota item = new ItemNota(p1, 2.5); // 2.5kg do produto p1
        
        System.out.println("Subtotal do item: " + item.calcularSubtotal());

        // 5. Testando a Nota (Classe do seu colega)
        Nota nota = new Nota(1);
        nota.adicionarItem(item);
        
        System.out.println("Total da nota: " + nota.calcularTotal());
    }
}
