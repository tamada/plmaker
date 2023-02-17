package jp.cafebabe.plmaker.entities;

import jp.cafebabe.plmaker.PLMakerException;

import java.util.Objects;

public class Product {
    private String owner;
    private String repository;

    public Product(String owner, String repository) {
        this.owner = owner;
        this.repository = repository;
    }

    @Override
    public int hashCode() {
        return Objects.hash(owner, repository);
    }

    @Override
    public boolean equals(Object other) {
        if(other == null) return false;
        if(other == this) return true;
        if(other.getClass() != getClass()) return false;
        Product p = (Product) other;
        return Objects.equals(owner, p.owner)
                && Objects.equals(repository, p.repository);
    }

    public void accept(Visitor visitor) {
        visitor.visitProduct(owner, repository);
    }

    public String owner() {
        return owner;
    }

    public String repository() {
        return repository;
    }

    public static Product of(String ownerRepository) {
        String[] items = ownerRepository.split("/");
        if(items.length == 2)
            return new Product(items[0], items[1]);
        throw new PLMakerException(String.format("%s: should form OWNER/PRODUCT"));
    }
}
