//package ru.bobcody.data.services;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Profile;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import ru.bobcody.domain.Quote;
//import ru.bobcody.repository.QuoteRepository;
//
//import java.time.LocalDateTime;
//
//@Service
//@Profile(value = {"test"})
//@RequiredArgsConstructor(onConstructor = @__(@Autowired))
//public class QuoteService {
//    private final QuoteRepository quoteRepository;
//
//    @Transactional
//    public Quote save(Quote quote) {
//        return quoteRepository.save(quote);
//    }
//
//    public Quote getById(long id) {
//        return quoteRepository.findById(id).orElse(null);
//    }
//
//    @Transactional
//    public boolean approveCaps(long quoteId) {
//        return quoteRepository.approveCapsHSQL(quoteId, LocalDateTime.now()) == 1;
//    }
//
//    @Transactional
//    public boolean approveRegular(long quoteId) {
//        return quoteRepository.approveRegularHSQL(quoteId, LocalDateTime.now()) == 1;
//    }
//
//    public Quote getByCapsId(long id) {
//        return quoteRepository.findByCapsId(id);
//    }
//
//    public Quote getByRegularId(long id) {
//        return quoteRepository.findByRegularId(id);
//    }
//
//    public long getRegularId(long id) {
//        return getById(id).getRegularId();
//    }
//
//    public long getCapsId(long id) {
//        return getById(id).getCapsId();
//    }
//
//    public long getLastRegularId() {
//        return quoteRepository.lastRegularId();
//    }
//
//    public long getLastCapsId() {
//        return quoteRepository.lastCapsId();
//    }
//
//}
