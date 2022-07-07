package com.laserfiche.api.client.unit;

import com.laserfiche.api.client.httphandlers.HeaderKeyValue;
import com.laserfiche.api.client.httphandlers.HeaderKeyValueImpl;
import com.laserfiche.api.client.httphandlers.Headers;
import com.laserfiche.api.client.httphandlers.HeadersImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class HeadersImplTest {
    private Headers headers;
    private static List<HeaderKeyValue> testingHeaders;
    private static final Random rand = new Random(Instant.now().toEpochMilli()); // Different seed each time;

    @BeforeAll
    static void setup() {
        testingHeaders = new ArrayList<>();
        testingHeaders.add(new HeaderKeyValueImpl("Accept", "text/plain"));
        testingHeaders.add(new HeaderKeyValueImpl("Accept-Charset", "utf-8"));
        testingHeaders.add(new HeaderKeyValueImpl("Accept-Encoding", "gzip, deflate"));
        testingHeaders.add(new HeaderKeyValueImpl("Accept-Language", "en-US"));
        testingHeaders.add(new HeaderKeyValueImpl("Authorization", "Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ=="));
        testingHeaders.add(new HeaderKeyValueImpl("Cache-Control", "no-cache"));
        testingHeaders.add(new HeaderKeyValueImpl("Connection", "keep-alive"));
        testingHeaders.add(new HeaderKeyValueImpl("Content-Length", "348"));
        testingHeaders.add(new HeaderKeyValueImpl("Content-MD5", "Q2hlY2sgSW50ZWdyaXR5IQ=="));
        testingHeaders.add(new HeaderKeyValueImpl("Content-Type", "application/json"));
        testingHeaders.add(new HeaderKeyValueImpl("Date", "Fri, 5 Oct 1990 08:12:31 GMT"));
        testingHeaders.add(new HeaderKeyValueImpl("Expect", "100-continue"));
        testingHeaders.add(new HeaderKeyValueImpl("From", "user@example.com"));
        testingHeaders.add(new HeaderKeyValueImpl("Host", "en.wikipedia.org"));
        testingHeaders.add(new HeaderKeyValueImpl("If-Match", "737060cd8c284d8af7ad3082f209582d"));
        testingHeaders.add(new HeaderKeyValueImpl("If-Modified-Since", "Fri, 5 Oct 1990 19:43:31 GMT"));
        testingHeaders.add(new HeaderKeyValueImpl("If-None-Match", "737060cd8c284d8af7ad3082f209582d"));
        testingHeaders.add(new HeaderKeyValueImpl("If-Range", "737060cd8c284d8af7ad3082f209582d"));
        testingHeaders.add(new HeaderKeyValueImpl("If-Unmodified-Since", "Fri, 5 Oct 1990 19:43:31 GMT"));
        testingHeaders.add(new HeaderKeyValueImpl("Max-Forwards", "10"));
        testingHeaders.add(new HeaderKeyValueImpl("Pragma", "no-cache"));
        testingHeaders.add(new HeaderKeyValueImpl("Proxy-Authorization", "Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ=="));
        testingHeaders.add(new HeaderKeyValueImpl("Range", "bytes=500-999"));
        testingHeaders.add(new HeaderKeyValueImpl("Referer", "http://en.wikipedia.org/wiki/Main_Page"));
        testingHeaders.add(new HeaderKeyValueImpl("TE", "trailers, deflate"));
        testingHeaders.add(new HeaderKeyValueImpl("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/34.0.1847.116 Safari/537.36"));
        testingHeaders.add(new HeaderKeyValueImpl("Via", "1.0 fred, 1.1 example.com (Apache/1.1)"));
        testingHeaders.add(new HeaderKeyValueImpl("Warning", "199 Miscellaneous warning"));
        testingHeaders.add(new HeaderKeyValueImpl("Cookie", "$Version=1; Skin=new;"));
        testingHeaders.add(new HeaderKeyValueImpl("Origin", "http://www.example-social-network.com"));
        testingHeaders.add(new HeaderKeyValueImpl("Accept-Datetime", "Thu, 31 May 2007 20:35:00 GMT"));
    }

    private static HeaderKeyValue getHeaderRandomly() {
        int index = rand.nextInt(testingHeaders.size());
        return testingHeaders.get(index);
    }

    @BeforeEach
    void perTestSetup() {
        headers = new HeadersImpl();
    }

    @Test
    void append_ShouldAddHeaderWhenHeaderNameNotSet() {
        HeaderKeyValue pair = getHeaderRandomly();
        headers.append(pair.headerName(), pair.header());

        assertTrue(headers.has(pair.headerName()));
        assertEquals(pair.header(), headers.get(pair.headerName()));
    }

    @Test
    void append_ShouldAddHeaderWhenHeaderNameAlreadySet() {
        HeaderKeyValue pair1 = getHeaderRandomly();
        HeaderKeyValue pair2 = getHeaderRandomly();
        headers.append(pair1.headerName(), pair1.header());
        headers.append(pair1.headerName(), pair2.header());
        String expected = String.format("%s, %s", pair1.header(), pair2.header());

        assertTrue(headers.has(pair1.headerName()));
        assertEquals(expected, headers.get(pair1.headerName()));
    }

    @Test
    void append_ShouldNotSetHeaderIfNull() {
        HeaderKeyValue pair = getHeaderRandomly();
        headers.append(pair.headerName(), null);

        assertFalse(headers.has(pair.headerName()));
    }

    @Test
    void delete_ShouldRemoveHeader() {
        HeaderKeyValue pair = getHeaderRandomly();
        headers.append(pair.headerName(), pair.header());
        headers.delete(pair.headerName());

        assertFalse(headers.has(pair.headerName()));
    }

    @Test
    void delete_ShouldDoNothingWhenHeaderDoesNotExist() {
        HeaderKeyValue pair = getHeaderRandomly();
        headers.delete(pair.headerName());

        assertEquals(0, headers.entries().size());
    }

    @Test
    void entries_ShouldReturnAllHeaders() {
        HeaderKeyValue pair1 = testingHeaders.get(0);
        HeaderKeyValue pair2 = testingHeaders.get(1);
        HeaderKeyValue pair3 = testingHeaders.get(2);
        headers.append(pair1.headerName(), pair1.header());
        headers.append(pair2.headerName(), pair2.header());
        headers.append(pair3.headerName(), pair3.header());

        Collection<HeaderKeyValue> entries = headers.entries();
        assertEquals(3, entries.size());

        entries.forEach((kvPair) -> assertTrue(
                (kvPair.headerName().equals(pair1.headerName()) && kvPair.header().equals(pair1.header())) ||
                        (kvPair.headerName().equals(pair2.headerName()) && kvPair.header().equals(pair2.header())) ||
                        (kvPair.headerName().equals(pair3.headerName()) && kvPair.header().equals(pair3.header()))));
    }

    @Test
    void get_ShouldReturnNullIfHeaderDoesNotExist() {
        HeaderKeyValue pair = getHeaderRandomly();

        assertFalse(headers.has(pair.headerName()));
        assertNull(headers.get(pair.headerName()));
    }

    @Test
    void keys_ShouldReturnAllKeys() {
        HeaderKeyValue pair1 = testingHeaders.get(0);
        HeaderKeyValue pair2 = testingHeaders.get(1);
        HeaderKeyValue pair3 = testingHeaders.get(2);
        headers.append(pair1.headerName(), pair1.header());
        headers.append(pair2.headerName(), pair2.header());
        headers.append(pair3.headerName(), pair3.header());

        Set<String> keys = headers.keys();
        assertEquals(3, keys.size());

        keys.forEach((headerName) -> assertTrue(
                (headerName.equals(pair1.headerName())) ||
                        (headerName.equals(pair2.headerName())) ||
                        (headerName.equals(pair3.headerName()))));
    }

    @Test
    void set_ShouldSetHeader() {
        HeaderKeyValue pair = getHeaderRandomly();
        headers.set(pair.headerName(), pair.header());

        assertTrue(headers.has(pair.headerName()));
        assertEquals(pair.header(), headers.get(pair.headerName()));
    }

    @Test
    void values_ShouldReturnAllValues() {
        HeaderKeyValue pair1 = testingHeaders.get(0);
        HeaderKeyValue pair2 = testingHeaders.get(1);
        HeaderKeyValue pair3 = testingHeaders.get(2);
        headers.append(pair1.headerName(), pair1.header());
        headers.append(pair2.headerName(), pair2.header());
        headers.append(pair3.headerName(), pair3.header());

        Collection<String> values = headers.values();
        assertEquals(3, values.size());

        values.forEach((header) -> assertTrue(
                header.equals(pair1.header()) ||
                header.equals(pair2.header()) ||
                header.equals(pair3.header())));
    }
}
