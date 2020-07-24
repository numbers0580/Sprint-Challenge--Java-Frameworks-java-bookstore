package com.lambdaschool.bookstore.services;

import com.lambdaschool.bookstore.BookstoreApplication;
import com.lambdaschool.bookstore.exceptions.ResourceNotFoundException;
import com.lambdaschool.bookstore.models.Book;
import com.lambdaschool.bookstore.models.Section;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static org.junit.Assert.*;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BookstoreApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
//**********
// Note security is handled at the controller, hence we do not need to worry about security here!
//**********
public class BookServiceImplTest
{

    @Autowired
    private BookService bookService;

    @Autowired
    private SectionService sectionService;

    @Before
    public void setUp() throws
            Exception
    {
        MockitoAnnotations.initMocks(this);
        List<Book> books = bookService.findAll();

        books.forEach(book -> System.out.format("%s: %s \n", book.getBookid(), book.getTitle()));
    }

    @After
    public void tearDown() throws
            Exception
    {
    }

    @Test
    public void a_findAll()
    {
        assertEquals(5, bookService.findAll().size());
    }

    @Test
    public void b_findBookById()
    {
        assertEquals("Calling Texas Home", bookService.findBookById(30).getTitle());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void c_notFindBookById()
    {
        assertEquals("Calling Texas Home", bookService.findBookById(999).getTitle());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void d_delete()
    {
        bookService.findBookById(26);
        bookService.delete(26);
        bookService.findBookById(26);
    }

    // Post
    @Test
    public void e_save()
    {
        String title = "You Don't Know JavaScript";
        Book b2 = new Book(title, "9788489367012", 2007, sectionService.findSectionById(21));

        Book newBook = bookService.save(b2);
        assertNotNull(newBook);
        assertEquals(newBook.getTitle(), title);
    }

    @Test
    public void eb_savePutVer() {
        String title = "You Don't Know JavaScript";
        Book b2 = new Book(title, "9788489367012", 2007, sectionService.findSectionById(21));
        b2.setBookid(29);

        Book newBook = bookService.save(b2);
        assertNotNull(newBook);
        assertEquals(newBook.getTitle(), title);
    }

    @Test
    public void f_update()
    {
        String title = "You Don't Know JavaScript";
        Book b2 = new Book(title, "9788489367012", 2007, sectionService.findSectionById(21));
        b2.setBookid(28);

        Book newBook = bookService.update(b2, 28);
        assertNotNull(newBook);
        assertEquals(newBook.getTitle(), title);
    }

    @Test
    public void z_deleteAll()
    {
        bookService.deleteAll();
        assertEquals(0, bookService.findAll().size());
    }
}