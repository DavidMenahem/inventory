package com.dvmena.inventory.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "sub_categories")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubCategory {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;

    @Column(name="name",nullable = false)
    private String name;

    @Column(name="description",nullable = false)
    private String description;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "SubCategory{" +
                "id=" + id +
                ", category=" + category.getName() +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
